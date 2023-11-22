/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.models

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.JsonObject

/**
 * Represents an `assistant` that can call the model and use tools.
 *
 * @param id The identifier, which can be referenced in API endpoints.
 * @param `object` The object type, which is always `assistant`.
 * @param createdAt The Unix timestamp (in seconds) for when the assistant was created.
 * @param name The name of the assistant. The maximum length is 256 characters.
 * @param description The description of the assistant. The maximum length is 512 characters.
 * @param model ID of the model to use. You can use the
 *   [List models](/docs/api-reference/models/list) API to see all of your available models, or see
 *   our [Model overview](/docs/models/overview) for descriptions of them.
 * @param instructions The system instructions that the assistant uses. The maximum length is 32768
 *   characters.
 * @param tools A list of tool enabled on the assistant. There can be a maximum of 128 tools per
 *   assistant. Tools can be of types `code_interpreter`, `retrieval`, or `function`.
 * @param fileIds A list of [file](/docs/api-reference/files) IDs attached to this assistant. There
 *   can be a maximum of 20 files attached to the assistant. Files are ordered by their creation
 *   date in ascending order.
 * @param metadata Set of 16 key-value pairs that can be attached to an object. This can be useful
 *   for storing additional information about the object in a structured format. Keys can be a
 *   maximum of 64 characters long and values can be a maxium of 512 characters long.
 */
@Serializable
data class AssistantObject(

  /* The identifier, which can be referenced in API endpoints. */
  @SerialName(value = "id") @Required val id: kotlin.String,

  /* The object type, which is always `assistant`. */
  @SerialName(value = "object") @Required val `object`: AssistantObject.`Object`,

  /* The Unix timestamp (in seconds) for when the assistant was created. */
  @SerialName(value = "created_at") @Required val createdAt: kotlin.Int,

  /* The name of the assistant. The maximum length is 256 characters.  */
  @SerialName(value = "name") @Required val name: kotlin.String?,

  /* The description of the assistant. The maximum length is 512 characters.  */
  @SerialName(value = "description") @Required val description: kotlin.String?,

  /* ID of the model to use. You can use the [List models](/docs/api-reference/models/list) API to see all of your available models, or see our [Model overview](/docs/models/overview) for descriptions of them.  */
  @SerialName(value = "model") @Required val model: kotlin.String,

  /* The system instructions that the assistant uses. The maximum length is 32768 characters.  */
  @SerialName(value = "instructions") @Required val instructions: kotlin.String?,

  /* A list of tool enabled on the assistant. There can be a maximum of 128 tools per assistant. Tools can be of types `code_interpreter`, `retrieval`, or `function`.  */
  @SerialName(value = "tools")
  @Required
  val tools: kotlin.collections.List<AssistantObjectToolsInner> = arrayListOf(),

  /* A list of [file](/docs/api-reference/files) IDs attached to this assistant. There can be a maximum of 20 files attached to the assistant. Files are ordered by their creation date in ascending order.  */
  @SerialName(value = "file_ids")
  @Required
  val fileIds: kotlin.collections.List<kotlin.String> = arrayListOf(),

  /* Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.  */
  @SerialName(value = "metadata") @Required val metadata: JsonObject?
) {

  /**
   * The object type, which is always `assistant`.
   *
   * Values: assistant
   */
  @Serializable
  enum class `Object`(val value: kotlin.String) {
    @SerialName(value = "assistant") assistant("assistant")
  }
}
