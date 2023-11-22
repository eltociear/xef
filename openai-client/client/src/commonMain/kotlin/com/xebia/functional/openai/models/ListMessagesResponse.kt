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
 * @param `object`
 * @param `data`
 * @param firstId
 * @param lastId
 * @param hasMore
 */
@Serializable
data class ListMessagesResponse(
  @SerialName(value = "object") @Required val `object`: kotlin.String,
  @SerialName(value = "data") @Required val `data`: kotlin.collections.List<MessageObject>,
  @SerialName(value = "first_id") @Required val firstId: kotlin.String? = null,
  @SerialName(value = "last_id") @Required val lastId: kotlin.String? = null,
  @SerialName(value = "has_more") @Required val hasMore: kotlin.Boolean
)
