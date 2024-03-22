/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.generated.model

import kotlinx.serialization.*

/**
 * The model to use for image generation.
 *
 * Values: _2,_3
 */
@Serializable
enum class CreateImageRequestModel(val value: kotlin.String) {

  @SerialName(value = "dall-e-2") _2("dall-e-2"),
  @SerialName(value = "dall-e-3") _3("dall-e-3");

  /**
   * Override [toString()] to avoid using the enum variable name as the value, and instead use the
   * actual value defined in the API spec file.
   *
   * This solves a problem when the variable name and its value are different, and ensures that the
   * client sends the correct enum values to the server always.
   */
  override fun toString(): kotlin.String = value
}