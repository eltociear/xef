/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.generated.model

import kotlin.jvm.JvmInline

sealed interface ChatCompletionToolChoiceOption {

  @JvmInline
  value class First(val value: ChatCompletionNamedToolChoice) : ChatCompletionToolChoiceOption

  @JvmInline value class Second(val value: kotlin.String) : ChatCompletionToolChoiceOption
}