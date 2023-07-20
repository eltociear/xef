package com.xebia.functional.gpt4all

import ai.djl.training.util.DownloadUtils
import ai.djl.training.util.ProgressBar
import com.hexadevlabs.gpt4all.LLModel
import com.xebia.functional.tokenizer.EncodingType
import com.xebia.functional.tokenizer.ModelType
import com.xebia.functional.xef.auto.PromptConfiguration
import com.xebia.functional.xef.llm.Chat
import com.xebia.functional.xef.llm.Completion
import com.xebia.functional.xef.llm.models.chat.*
import com.xebia.functional.xef.llm.models.text.CompletionChoice
import com.xebia.functional.xef.llm.models.text.CompletionRequest
import com.xebia.functional.xef.llm.models.text.CompletionResult
import com.xebia.functional.xef.llm.models.usage.Usage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.name

class GPT4AllPromptConfiguration : PromptConfiguration() {
    val topK: Int = 40
    val topP: Float = 0.95f
    val nPredict: Int = 4096
    val nCtx: Int = 1024
    val nPast: Int = 0
    val nBatch: Int = 8
    val repeatPenalty: Float = 1.1f
    val repeatLastN: Int = 64
    val contextErase: Float = 0.55f

    companion object {
        @JvmField
        val DEFAULTS = GPT4AllPromptConfiguration()
    }

    fun toLLModelConfig(): LLModel.GenerationConfig = LLModel.config()
        .withTemp(temperature.toFloat())
        .withTopK(topK)
        .withTopP(topP)
        .withNPredict(nPredict)
        .withNCtx(nCtx)
        .withNPast(nPast)
        .withNBatch(nBatch)
        .withRepeatPenalty(repeatPenalty)
        .withRepeatLastN(repeatLastN)
        .withContextErase(contextErase)
        .build()
}

interface GPT4All : AutoCloseable, Chat, Completion {

    override fun close() {
    }

    companion object {

        operator fun invoke(
            url: String,
            path: Path
        ): GPT4All = object : GPT4All {

            init {
                if (!Files.exists(path)) {
                    DownloadUtils.download(url, path.toFile().absolutePath, ProgressBar())
                }
            }

            val llModel = LLModel(path)


            override suspend fun createCompletion(request: CompletionRequest): CompletionResult =
                with(request) {
                    val promptConfig: GPT4AllPromptConfiguration =
                        request.promptConfiguration as GPT4AllPromptConfiguration
                    val config = promptConfig.toLLModelConfig()
                    val response: String = generateCompletion(prompt, config, request.streamToStandardOut)
                    return CompletionResult(
                        UUID.randomUUID().toString(),
                        path.name,
                        System.currentTimeMillis(),
                        path.name,
                        listOf(CompletionChoice(response, 0, null, null)),
                        Usage.ZERO
                    )
                }

            override suspend fun createChatCompletion(request: ChatCompletionRequest): ChatCompletionResponse =
                with(request) {
                    val prompt: String = messages.buildPrompt()
                    val promptConfig: GPT4AllPromptConfiguration =
                        request.promptConfiguration as GPT4AllPromptConfiguration
                    val config = promptConfig.toLLModelConfig()
                    val response: String = generateCompletion(prompt, config, request.streamToStandardOut)
                    return ChatCompletionResponse(
                        UUID.randomUUID().toString(),
                        path.name,
                        System.currentTimeMillis().toInt(),
                        path.name,
                        Usage.ZERO,
                        listOf(Choice(Message(Role.ASSISTANT, response, Role.ASSISTANT.name), null, 0)),
                    )
                }

            /**
             * Creates chat completions based on the given ChatCompletionRequest.
             *
             * hacks the System.out until https://github.com/nomic-ai/gpt4all/pull/1126 is accepted or merged
             *
             * @param request The ChatCompletionRequest containing the necessary information for creating completions.
             * @return A Flow of ChatCompletionChunk objects representing the generated chat completions.
             */
            override suspend fun createChatCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk> =
                with(request) {
                    val prompt: String = messages.buildPrompt()
                    val promptConfig: GPT4AllPromptConfiguration =
                        request.promptConfiguration as GPT4AllPromptConfiguration
                    val config = promptConfig.toLLModelConfig()

                    val originalOut = System.out // Save the original standard output

                    return coroutineScope {
                        val channel = Channel<String>(capacity = UNLIMITED)

                        val outputStream = object : OutputStream() {
                            override fun write(b: Int) {
                                val c = b.toChar()
                                channel.trySend(c.toString())
                            }
                        }

                        val printStream = PrintStream(outputStream, true, StandardCharsets.UTF_8)

                        fun toChunk(text: String?): ChatCompletionChunk =
                            ChatCompletionChunk(
                                UUID.randomUUID().toString(),
                                System.currentTimeMillis().toInt(),
                                path.name,
                                listOf(ChatChunk(delta = ChatDelta(Role.ASSISTANT, text))),
                                Usage.ZERO,
                            )

                        val flow = channel.consumeAsFlow().map { toChunk(it) }

                        launch(Dispatchers.IO) {
                            System.setOut(printStream) // Set the standard output to the print stream
                            generateCompletion(prompt, config, request.streamToStandardOut)
                            channel.close()
                        }

                        flow.onCompletion {
                            System.setOut(originalOut) // Restore the original standard output
                        }
                    }
                }

            override fun tokensFromMessages(messages: List<Message>): Int {
                return 0
            }

            override val name: String = path.name

            override fun close(): Unit = llModel.close()

            override val modelType: ModelType = ModelType.LocalModel(name, EncodingType.CL100K_BASE, 4096)

            private fun List<Message>.buildPrompt(): String {
                val messages: String = joinToString("") { message ->
                    when (message.role) {
                        Role.SYSTEM -> message.content
//            Role.USER -> "\n###Human: ${message.content}"
//            Role.USER -> "\n### User: ${message.content}"
                        Role.USER -> "\n### Instruction: ${message.content}"
                        Role.ASSISTANT -> "\n### Response: ${message.content}"
                    }
                }
                return "$messages\n### Response:"
            }

            private fun generateCompletion(
                prompt: String,
                config: LLModel.GenerationConfig,
                stream: Boolean,
            ): String {
                return llModel.generate(prompt, config, stream)
            }
        }

    }
}
