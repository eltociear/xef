/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param created
 * @param `data`
 */
@Serializable
data class ImagesResponse(
  @SerialName(value = "created") val created: kotlin.Int,
  @SerialName(value = "data") val `data`: kotlin.collections.List<Image>
)