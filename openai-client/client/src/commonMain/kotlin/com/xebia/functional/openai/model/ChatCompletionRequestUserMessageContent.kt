/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.model

import kotlin.jvm.JvmInline

sealed interface ChatCompletionRequestUserMessageContent {

  @JvmInline value class First(val value: kotlin.String) : ChatCompletionRequestUserMessageContent

  @JvmInline
  value class Second(val value: kotlin.collections.List<ChatCompletionRequestMessageContentPart>) :
    ChatCompletionRequestUserMessageContent
}