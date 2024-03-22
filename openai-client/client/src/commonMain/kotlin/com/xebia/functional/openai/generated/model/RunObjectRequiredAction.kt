/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.generated.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Details on the action required to continue the run. Will be `null` if no action is required.
 *
 * @param type For now, this is always `submit_tool_outputs`.
 * @param submitToolOutputs
 */
@Serializable
data class RunObjectRequiredAction(
  /* For now, this is always `submit_tool_outputs`. */
  @SerialName(value = "type") val type: RunObjectRequiredAction.Type,
  @SerialName(value = "submit_tool_outputs")
  val submitToolOutputs: RunObjectRequiredActionSubmitToolOutputs
) {

  /**
   * For now, this is always `submit_tool_outputs`.
   *
   * Values: submit_tool_outputs
   */
  @Serializable
  enum class Type(val value: kotlin.String) {
    @SerialName(value = "submit_tool_outputs") submit_tool_outputs("submit_tool_outputs")
  }
}