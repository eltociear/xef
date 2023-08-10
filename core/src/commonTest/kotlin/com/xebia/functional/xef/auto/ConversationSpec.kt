package com.xebia.functional.xef.auto

import com.xebia.functional.tokenizer.ModelType
import com.xebia.functional.xef.data.TestEmbeddings
import com.xebia.functional.xef.data.TestModel
import com.xebia.functional.xef.vectorstores.ConversationId
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import kotlinx.uuid.UUID
import kotlinx.uuid.generateUUID

class ConversationSpec :
  StringSpec({
    "memories should have the correct size in the vector store" {
      val conversationId = ConversationId(UUID.generateUUID().toString())

      val model = TestModel(modelType = ModelType.ADA, name = "fake-model")

      val scope = Conversation(TestEmbeddings(), conversationId = conversationId)

      val vectorStore = scope.store

      model.promptMessages(question = "question 1", scope = scope)

      model.promptMessages(question = "question 2", scope = scope)

      val memories = vectorStore.memories(conversationId, 10)

      memories.size shouldBe 4
    }

    """"
      | ADA model has 2049 max context length
      | when the number of token in the conversation is greater than 
      | the space allotted for the message history in the prompt configuration
      | the number of messages in the request must have fewer messages than 
      | the total number of messages in the conversation
      |""" {
      val promptConfiguration = PromptConfiguration { memoryLimit(Int.MAX_VALUE) }
      val messages = generateRandomMessages(50, 40, 60)
      val conversationId = ConversationId(UUID.generateUUID().toString())
      val scope = Conversation(TestEmbeddings(), conversationId = conversationId)
      val vectorStore = scope.store

      val modelAda = TestModel(modelType = ModelType.ADA, name = "fake-model", responses = messages)

      messages.forEach { message ->
        modelAda.promptMessages(
          question = message.key,
          scope = scope,
          promptConfiguration = promptConfiguration
        )
      }

      val lastRequest = modelAda.requests.last()

      val memories = vectorStore.memories(conversationId, promptConfiguration.memoryLimit)

      // The messages in the request doesn't contain the message response
      val messagesSizePlusMessageResponse = lastRequest.messages.size + 1

      messagesSizePlusMessageResponse shouldBeLessThan memories.size
    }

    """"
      | GPT Turbo 16K model has 16388 max context length
      | when the number of token in the conversation is less than 
      | the space allotted for the message history in the prompt configuration
      | the request must send all messages in the conversation
      |""" {
      val promptConfiguration = PromptConfiguration { memoryLimit(Int.MAX_VALUE) }
      val messages = generateRandomMessages(50, 40, 60)
      val conversationId = ConversationId(UUID.generateUUID().toString())
      val scope = Conversation(TestEmbeddings(), conversationId = conversationId)
      val vectorStore = scope.store

      val modelGPTTurbo16K =
        TestModel(
          modelType = ModelType.GPT_3_5_TURBO_16_K,
          name = "fake-model",
          responses = messages
        )

      messages.forEach { message ->
        modelGPTTurbo16K.promptMessages(
          question = message.key,
          scope = scope,
          promptConfiguration = promptConfiguration
        )
      }

      val lastRequest = modelGPTTurbo16K.requests.last()

      val memories = vectorStore.memories(conversationId, promptConfiguration.memoryLimit)

      // The messages in the request doesn't contain the message response
      val messagesSizePlusMessageResponse = lastRequest.messages.size + 1

      messagesSizePlusMessageResponse shouldBe memories.size
    }
  })
