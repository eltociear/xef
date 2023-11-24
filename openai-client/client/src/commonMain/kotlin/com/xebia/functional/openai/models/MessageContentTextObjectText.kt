/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.models

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

/**
 * @param `value` The data that makes up the text.
 * @param annotations
 */
@Serializable
data class MessageContentTextObjectText(

  /* The data that makes up the text. */
  @SerialName(value = "value") @Required val `value`: kotlin.String,
  @SerialName(value = "annotations")
  @Required
  val annotations: kotlin.collections.List<MessageContentTextObjectTextAnnotationsInner>
)
