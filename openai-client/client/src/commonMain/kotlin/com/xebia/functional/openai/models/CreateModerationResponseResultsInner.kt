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
 * @param flagged Whether the content violates [OpenAI's usage policies](/policies/usage-policies).
 * @param categories
 * @param categoryScores
 */
@Serializable
data class CreateModerationResponseResultsInner(

  /* Whether the content violates [OpenAI's usage policies](/policies/usage-policies). */
  @SerialName(value = "flagged") @Required val flagged: kotlin.Boolean,
  @SerialName(value = "categories")
  @Required
  val categories: CreateModerationResponseResultsInnerCategories,
  @SerialName(value = "category_scores")
  @Required
  val categoryScores: CreateModerationResponseResultsInnerCategoryScores
)