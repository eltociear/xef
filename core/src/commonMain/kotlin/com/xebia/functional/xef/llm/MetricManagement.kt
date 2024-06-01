package com.xebia.functional.xef.llm

import com.xebia.functional.xef.conversation.Conversation
import com.xebia.functional.xef.prompt.Prompt

suspend fun CreateChatCompletionResponse.addMetrics(
  conversation: Conversation
): CreateChatCompletionResponse {
  conversation.metric.parameter("openai.chat_completion.model", model)
  usage?.let {
    conversation.metric.parameter("openai.chat_completion.prompt.token.count", "${it.inputTokenCount}")
    conversation.metric.parameter(
      "openai.chat_completion.completion.token.count",
      "${it.outputTokenCount}"
    )
    conversation.metric.parameter("openai.chat_completion.token.count", "${it.totalTokenCount}")
  }
  choices.forEach { choice ->
    choice.message.content?.let {
      conversation.metric.parameter("openai.chat_completion.content", it)
    }
    choice.message.toolCalls?.forEach {
      toolCall ->

      conversation.metric.parameter(
        "openai.chat_completion.tool_call.name",
        toolCall.function.functionName
      )

      conversation.metric.parameter(
        "openai.chat_completion.tool_call.arguments",
        toolCall.function.arguments
      )
    }
  }
  return this
}

suspend fun Prompt.addMetrics(conversation: Conversation) {
  conversation.metric.parameter("openai.chat_completion.prompt.message.count", "${messages.size}")

  conversation.metric.parameter(
    "openai.chat_completion.prompt.messages_roles",
    messages.map { it.role.name }
  )
  conversation.metric.parameter(
    "openai.chat_completion.prompt.last-message",
    messages.lastOrNull()?.content ?: "empty"
  )
  conversation.metric.parameter(
    "openai.chat_completion.conversation_id",
    conversation.conversationId?.value ?: "none"
  )
  conversation.metric.parameter(
    "openai.chat_completion.prompt.temperature",
    "${configuration.temperature}"
  )
  if (functions.isNotEmpty())
    conversation.metric.parameter("openai.chat_completion.functions", functions.map { it.name })
}
