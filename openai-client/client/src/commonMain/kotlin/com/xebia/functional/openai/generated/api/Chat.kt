/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.generated.api

import com.xebia.functional.openai.generated.api.Chat.*
import com.xebia.functional.openai.generated.model.CreateChatCompletionRequest
import com.xebia.functional.openai.generated.model.CreateChatCompletionResponse
import com.xebia.functional.openai.generated.model.CreateChatCompletionStreamResponse
import com.xebia.functional.openai.streamEvents
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json

/**  */
interface Chat {

  /**
   * Creates a model response for the given chat conversation.
   *
   * @param createChatCompletionRequest
   * @return CreateChatCompletionResponse
   */
  suspend fun createChatCompletion(
    createChatCompletionRequest: CreateChatCompletionRequest
  ): CreateChatCompletionResponse

  /**
   * Streaming variant: Creates a model response for the given chat conversation.
   *
   * @param createChatCompletionRequest
   * @return [Flow]<[CreateChatCompletionStreamResponse]>
   */
  fun createChatCompletionStream(
    createChatCompletionRequest: CreateChatCompletionRequest
  ): Flow<CreateChatCompletionStreamResponse>
}

fun Chat(client: HttpClient): Chat =
  object : Chat {
    override suspend fun createChatCompletion(
      createChatCompletionRequest: CreateChatCompletionRequest,
    ): CreateChatCompletionResponse =
      client
        .request {
          method = HttpMethod.Post
          contentType(ContentType.Application.Json)
          url { path("/chat/completions") }
          setBody(createChatCompletionRequest)
        }
        .body()

    override fun createChatCompletionStream(
      createChatCompletionRequest: CreateChatCompletionRequest
    ): Flow<CreateChatCompletionStreamResponse> = flow {
      client
        .prepareRequest {
          method = HttpMethod.Post
          timeout {
            requestTimeoutMillis = 60.seconds.toLong(DurationUnit.MILLISECONDS)
            socketTimeoutMillis = 60.seconds.toLong(DurationUnit.MILLISECONDS)
          }
          accept(ContentType.Text.EventStream)
          header(HttpHeaders.CacheControl, "no-cache")
          header(HttpHeaders.Connection, "keep-alive")
          contentType(ContentType.Application.Json)
          url { path("/chat/completions") }
          setBody(createChatCompletionRequest)
        }
        .execute(::streamEvents)
    }
  }