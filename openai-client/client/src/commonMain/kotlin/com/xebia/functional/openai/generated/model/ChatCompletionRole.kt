/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.generated.model

import com.xebia.functional.openai.generated.model.ChatCompletionRole.Supported.*
import kotlin.jvm.JvmStatic
import kotlinx.serialization.*
import kotlinx.serialization.builtins.*
import kotlinx.serialization.encoding.*

/**
 * The role of the author of a message
 *
 * Values: system,user,assistant,tool,function
 */
// We define a serializer for the parent sum type,
// and then use it to serialize the child types
@Suppress("SERIALIZER_TYPE_INCOMPATIBLE")
@Serializable(with = ChatCompletionRoleSerializer::class)
sealed interface ChatCompletionRole {
  val value: kotlin.String

  @Serializable(with = ChatCompletionRoleSerializer::class)
  enum class Supported(override val value: kotlin.String) : ChatCompletionRole {

    @SerialName(value = "system") system("system"),
    @SerialName(value = "user") user("user"),
    @SerialName(value = "assistant") assistant("assistant"),
    @SerialName(value = "tool") tool("tool"),
    @SerialName(value = "function") function("function");

    override fun toString(): kotlin.String = value
  }

  @Serializable(with = ChatCompletionRoleSerializer::class)
  data class Custom(override val value: kotlin.String) : ChatCompletionRole

  companion object {
    @JvmStatic
    fun fromValue(value: kotlin.String): ChatCompletionRole =
      values().firstOrNull { it.value == value } ?: Custom(value)

    inline val system: ChatCompletionRole
      get() = Supported.system

    inline val user: ChatCompletionRole
      get() = Supported.user

    inline val assistant: ChatCompletionRole
      get() = Supported.assistant

    inline val tool: ChatCompletionRole
      get() = Supported.tool

    inline val function: ChatCompletionRole
      get() = Supported.function

    @JvmStatic fun values(): List<ChatCompletionRole> = Supported.entries

    @JvmStatic fun serializer(): KSerializer<ChatCompletionRole> = ChatCompletionRoleSerializer
  }
}

private object ChatCompletionRoleSerializer : KSerializer<ChatCompletionRole> {
  private val valueSerializer = kotlin.String.serializer()
  override val descriptor = valueSerializer.descriptor

  override fun deserialize(decoder: Decoder): ChatCompletionRole {
    val value = decoder.decodeSerializableValue(valueSerializer)
    return ChatCompletionRole.fromValue(value)
  }

  override fun serialize(encoder: Encoder, value: ChatCompletionRole) {
    encoder.encodeSerializableValue(valueSerializer, value.value)
  }
}
