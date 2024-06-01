package xef

import arrow.atomic.AtomicInt
import com.xebia.functional.xef.llm.ChatCompletionRequestMessage
import com.xebia.functional.xef.llm.Role
import com.xebia.functional.xef.store.ConversationId
import com.xebia.functional.xef.store.MemorizedMessage
import com.xebia.functional.xef.store.Memory

class MemoryData {
  val defaultConversationId = ConversationId("default-id")

  val atomicInt = AtomicInt(0)

  fun generateRandomMessages(
    n: Int,
    append: String? = null,
    conversationId: ConversationId = defaultConversationId
  ): List<Memory> =
    (0 until n).flatMap {
      val m1 = ChatCompletionRequestMessage(
        role = Role.user,
        content = "Question $it${append?.let { ": $it" } ?: ""}",
        toolCallResults = null
      )
      val m2 = ChatCompletionRequestMessage(
        role = Role.assistant,
        content = "Answer $it${append?.let { ": $it" } ?: ""}",
        toolCallResults = null
      )
      listOf(
        Memory(conversationId, MemorizedMessage.Request(m1), atomicInt.addAndGet(1)),
        Memory(conversationId, MemorizedMessage.Request(m2), atomicInt.addAndGet(1)),
      )
    }
}
