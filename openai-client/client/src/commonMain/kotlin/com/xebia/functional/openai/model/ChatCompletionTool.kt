/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param type The type of the tool. Currently, only `function` is supported.
 * @param function
 */
@Serializable
data class ChatCompletionTool(

  /* The type of the tool. Currently, only `function` is supported. */
  @SerialName(value = "type") val type: ChatCompletionTool.Type,
  @SerialName(value = "function") val function: FunctionObject
) {

  /**
   * The type of the tool. Currently, only `function` is supported.
   *
   * Values: function
   */
  @Serializable
  enum class Type(val value: kotlin.String) {
    @SerialName(value = "function") function("function")
  }
}