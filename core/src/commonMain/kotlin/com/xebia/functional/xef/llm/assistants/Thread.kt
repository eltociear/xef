package com.xebia.functional.xef.llm.assistants

import com.xebia.functional.openai.apis.AssistantsApi
import com.xebia.functional.openai.infrastructure.ApiClient
import com.xebia.functional.openai.models.*
import com.xebia.functional.xef.llm.fromEnvironment
import io.ktor.client.statement.*
import kotlin.jvm.JvmName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.JsonObject

class Thread(
  private val threadId: String,
  private val api: AssistantsApi = fromEnvironment(::AssistantsApi)
) {

  suspend fun delete(): Boolean = api.deleteThread(threadId).body().deleted

  suspend fun modify(request: ModifyThreadRequest): Thread =
    Thread(api.modifyThread(threadId, request).body().id)

  suspend fun createMessage(message: MessageWithFiles): MessageObject =
    api
      .createMessage(
        threadId,
        CreateMessageRequest(
          role = CreateMessageRequest.Role.user,
          content = message.content,
          fileIds = message.fileIds
        )
      )
      .body()

  suspend fun createMessage(content: String): MessageObject =
    api
      .createMessage(
        threadId,
        CreateMessageRequest(role = CreateMessageRequest.Role.user, content = content)
      )
      .body()

  suspend fun createMessage(request: CreateMessageRequest): MessageObject =
    api.createMessage(threadId, request).body()

  suspend fun getMessage(messageId: String): MessageObject =
    api.getMessage(threadId, messageId).body()

  suspend fun listMessages(): List<MessageObject> = api.listMessages(threadId).body().data

  suspend fun createRun(request: CreateRunRequest): RunObject =
    api.createRun(threadId, request).body()

  suspend fun getRun(runId: String): RunObject =
    api
      .getRun(threadId, runId)
      .also {
        //  it.response.bodyAsText().let(::println)
      }
      .body()

  suspend fun createRun(assistant: Assistant): RunObject =
    api
      .createRun(threadId, CreateRunRequest(assistantId = assistant.assistantId))
      .also {
        // it.response.bodyAsText().let(::println)
      }
      .body()

  suspend fun run(assistant: Assistant): Flow<RunDelta> {
    val run = createRun(assistant)
    return awaitRun(run.id)
  }

  suspend fun cancelRun(runId: String): RunObject = api.cancelRun(threadId, runId).body()

  suspend fun runSteps(runId: String): List<RunStepObject> =
    api
      .listRunSteps(threadId, runId)
      .also {
        // it.response.bodyAsText().let(::println)
      }
      .body()
      .data

  sealed class RunDelta {
    data class ReceivedMessage(val message: MessageObject) : RunDelta()

    data class Run(val message: RunObject) : RunDelta()

    data class Step(val runStep: RunStepObject) : RunDelta()
  }

  fun awaitRun(runId: String): Flow<RunDelta> = flow {
    val cache = mutableMapOf<String, RunDelta>()
    var run = getRun(runId)
    emit(RunDelta.Run(run))
    while (run.status != RunObject.Status.completed) {
      val steps = runSteps(runId)
      steps.forEach { step ->
        if (step.id !in cache) {
          cache[step.id] = RunDelta.Step(step)
          emit(RunDelta.Step(step))
        }
      }
      val messages = listMessages()
      messages.forEach { message ->
        if (message.id !in cache) {
          cache[message.id] = RunDelta.ReceivedMessage(message)
          emit(RunDelta.ReceivedMessage(message))
        }
      }
      run = getRun(runId)
      if (run.status == RunObject.Status.completed) {
        emit(RunDelta.Run(run))
      }
    }
  }

  companion object {

    @JvmName("createWithMessagesAndFiles")
    suspend operator fun invoke(
      messages: List<MessageWithFiles>,
      metadata: JsonObject? = null,
      api: AssistantsApi = fromEnvironment(::AssistantsApi)
    ): Thread =
      Thread(
        api
          .createThread(
            CreateThreadRequest(
              messages.map {
                CreateMessageRequest(
                  role = CreateMessageRequest.Role.user,
                  content = it.content,
                  fileIds = it.fileIds
                )
              },
              metadataAsString(metadata)
            )
          )
          .body()
          .id,
        api
      )

    private fun metadataAsString(metadata: JsonObject?): String? =
      metadata?.let { ApiClient.JSON_DEFAULT.encodeToString(it) }

    @JvmName("createWithMessages")
    suspend operator fun invoke(
      messages: List<String>,
      metadata: JsonObject? = null,
      api: AssistantsApi = fromEnvironment(::AssistantsApi)
    ): Thread =
      Thread(
        api
          .createThread(
            CreateThreadRequest(
              messages.map {
                CreateMessageRequest(role = CreateMessageRequest.Role.user, content = it)
              },
              metadataAsString(metadata)
            )
          )
          .body()
          .id,
        api
      )

    @JvmName("createWithRequests")
    suspend operator fun invoke(
      messages: List<CreateMessageRequest> = emptyList(),
      metadata: JsonObject? = null,
      api: AssistantsApi = fromEnvironment(::AssistantsApi)
    ): Thread =
      Thread(
        api.createThread(CreateThreadRequest(messages, metadataAsString(metadata))).body().id,
        api
      )

    suspend operator fun invoke(
      request: CreateThreadRequest,
      api: AssistantsApi = fromEnvironment(::AssistantsApi)
    ): Thread = Thread(api.createThread(request).body().id, api)

    suspend operator fun invoke(
      request: CreateThreadAndRunRequest,
      api: AssistantsApi = fromEnvironment(::AssistantsApi)
    ): Thread = Thread(api.createThreadAndRun(request).body().id, api)
  }
}
