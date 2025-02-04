package com.xebia.functional.xef.llm

import com.xebia.functional.openai.generated.model.ChatCompletionRequestMessage
import com.xebia.functional.tokenizer.truncateText
import com.xebia.functional.xef.AIError
import com.xebia.functional.xef.conversation.Conversation
import com.xebia.functional.xef.conversation.MessagesFromHistory
import com.xebia.functional.xef.llm.models.modelType
import com.xebia.functional.xef.prompt.Prompt
import com.xebia.functional.xef.prompt.PromptBuilder.Companion.assistant
import com.xebia.functional.xef.prompt.contentAsString
import com.xebia.functional.xef.store.Memory

internal object PromptCalculator {

  suspend fun adaptPromptToConversationAndModel(prompt: Prompt, scope: Conversation): Prompt =
    when (prompt.configuration.messagePolicy.addMessagesFromConversation) {
      MessagesFromHistory.ALL -> adaptPromptFromConversation(prompt, scope)
      MessagesFromHistory.NONE -> prompt
    }

  private suspend fun adaptPromptFromConversation(prompt: Prompt, scope: Conversation): Prompt {

    // calculate tokens for history and context
    val remainingTokensForContexts = calculateRemainingTokensForContext(prompt)

    val maxHistoryTokens = calculateMaxHistoryTokens(prompt, remainingTokensForContexts)

    val maxContextTokens = calculateMaxContextTokens(prompt, remainingTokensForContexts)

    // calculate messages for history based on tokens

    val memories: List<Memory> =
      scope.memories(
        prompt,
        maxHistoryTokens + prompt.configuration.messagePolicy.historyPaddingTokens
      )

    val historyAllowed = calculateMessagesFromHistory(prompt, memories, maxHistoryTokens)

    // calculate messages for context based on tokens
    val ctxInfo: List<String> =
      scope.store.similaritySearch(
        prompt.messages.joinToString("\n") { it.contentAsString() ?: "" },
        prompt.configuration.docsInContext,
      )

    val contextAllowed: List<ChatCompletionRequestMessage> =
      if (ctxInfo.isNotEmpty()) {
        val ctx: String = ctxInfo.joinToString("\n")

        val ctxTruncated: String =
          prompt.model.modelType().encodingType.encoding.truncateText(ctx, maxContextTokens)

        Prompt(
            model = prompt.model,
            functions = prompt.functions,
            configuration = prompt.configuration
          ) {
            +assistant(ctxTruncated)
          }
          .messages
      } else {
        emptyList()
      }

    return prompt.copy(messages = contextAllowed + historyAllowed + prompt.messages)
  }

  private fun messagesFromMemory(memories: List<Memory>): List<ChatCompletionRequestMessage> =
    memories.map { it.content.asRequestMessage() }

  private fun calculateMessagesFromHistory(
    prompt: Prompt,
    memories: List<Memory>,
    maxHistoryTokens: Int
  ) =
    if (memories.isNotEmpty()) {
      val history = messagesFromMemory(memories)

      // since we have the approximate tokens in memory, we need to fit the messages back to the
      // number of tokens if necessary
      val modelType = prompt.model.modelType()
      val historyTokens = modelType.tokensFromMessages(history)
      if (historyTokens <= maxHistoryTokens) history
      else {
        val historyMessagesWithTokens =
          history.map { Pair(it, modelType.tokensFromMessages(listOf(it))) }

        val totalTokenWithMessages =
          historyMessagesWithTokens.foldRight(Pair(0, emptyList<ChatCompletionRequestMessage>())) {
            pair,
            acc ->
            if (acc.first + pair.second > maxHistoryTokens) {
              acc
            } else {
              Pair(acc.first + pair.second, acc.second + pair.first)
            }
          }
        totalTokenWithMessages.second.reversed()
      }
    } else emptyList()

  private fun calculateMaxContextTokens(prompt: Prompt, remainingTokensForContexts: Int): Int {
    val contextPercent = prompt.configuration.messagePolicy.contextPercent
    val maxContextTokens = (remainingTokensForContexts * contextPercent) / 100
    return maxContextTokens
  }

  private fun calculateMaxHistoryTokens(prompt: Prompt, remainingTokensForContexts: Int): Int {
    val historyPercent = prompt.configuration.messagePolicy.historyPercent
    val maxHistoryTokens = (remainingTokensForContexts * historyPercent) / 100
    return maxHistoryTokens
  }

  private fun calculateRemainingTokensForContext(prompt: Prompt): Int {
    val maxContextLength: Int = prompt.model.modelType().maxContextLength
    val remainingTokens: Int = maxContextLength - prompt.configuration.maxTokens

    val messagesTokens = prompt.model.modelType().tokensFromMessages(prompt.messages)

    if (messagesTokens >= remainingTokens) {
      throw AIError.PromptExceedsMaxRemainingTokenLength(messagesTokens, remainingTokens)
    }

    val remainingTokensForContexts = remainingTokens - messagesTokens
    return remainingTokensForContexts
  }

  private suspend fun Conversation.memories(prompt: Prompt, limitTokens: Int): List<Memory> =
    conversationId?.let { store.memories(prompt.model, it, limitTokens) } ?: emptyList()
}
