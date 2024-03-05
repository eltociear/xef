/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.apis

import com.xebia.functional.openai.infrastructure.*
import com.xebia.functional.openai.models.CreateSpeechRequest
import com.xebia.functional.openai.models.CreateTranscriptionRequestModel
import com.xebia.functional.openai.models.CreateTranscriptionResponse
import com.xebia.functional.openai.models.CreateTranslationResponse
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.forms.formData
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json

open class AudioApi : ApiClient {

  constructor(
    baseUrl: String = ApiClient.BASE_URL,
    httpClientEngine: HttpClientEngine? = null,
    httpClientConfig: ((HttpClientConfig<*>) -> Unit)? = null,
    jsonSerializer: Json = ApiClient.JSON_DEFAULT
  ) : super(
    baseUrl = baseUrl,
    httpClientEngine = httpClientEngine,
    httpClientConfig = httpClientConfig,
    jsonBlock = jsonSerializer
  )

  constructor(
    baseUrl: String,
    httpClient: HttpClient
  ) : super(baseUrl = baseUrl, httpClient = httpClient)

  /**
   * Generates audio from the input text.
   *
   * @param createSpeechRequest
   * @return com.xebia.functional.openai.infrastructure.OctetByteArray
   */
  @Suppress("UNCHECKED_CAST")
  open suspend fun createSpeech(
    createSpeechRequest: CreateSpeechRequest
  ): HttpResponse<com.xebia.functional.openai.infrastructure.OctetByteArray> {

    val localVariableAuthNames = listOf<String>("ApiKeyAuth")

    val localVariableBody = createSpeechRequest

    val localVariableQuery = mutableMapOf<String, List<String>>()
    val localVariableHeaders = mutableMapOf<String, String>()

    val localVariableConfig =
      RequestConfig<kotlin.Any?>(
        RequestMethod.POST,
        "/audio/speech",
        query = localVariableQuery,
        headers = localVariableHeaders,
        requiresAuthentication = true,
      )

    return jsonRequest(localVariableConfig, localVariableBody, localVariableAuthNames).wrap()
  }

  /** enum for parameter responseFormat */
  @Serializable
  enum class ResponseFormatCreateTranscription(val value: kotlin.String) {

    @SerialName(value = "json") json("json"),
    @SerialName(value = "text") text("text"),
    @SerialName(value = "srt") srt("srt"),
    @SerialName(value = "verbose_json") verbose_json("verbose_json"),
    @SerialName(value = "vtt") vtt("vtt")
  }

  /** enum for parameter timestampGranularities */
  @Serializable
  enum class TimestampGranularitiesCreateTranscription(val value: kotlin.String) {

    @SerialName(value = "word") word("word"),
    @SerialName(value = "segment") segment("segment")
  }

  /**
   * Transcribes audio into the input language.
   *
   * @param file The audio file object (not file name) to transcribe, in one of these formats: flac,
   *   mp3, mp4, mpeg, mpga, m4a, ogg, wav, or webm.
   * @param model
   * @param language The language of the input audio. Supplying the input language in
   *   [ISO-639-1](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) format will improve
   *   accuracy and latency. (optional)
   * @param prompt An optional text to guide the model&#39;s style or continue a previous audio
   *   segment. The [prompt](/docs/guides/speech-to-text/prompting) should match the audio language.
   *   (optional)
   * @param responseFormat The format of the transcript output, in one of these options:
   *   &#x60;json&#x60;, &#x60;text&#x60;, &#x60;srt&#x60;, &#x60;verbose_json&#x60;, or
   *   &#x60;vtt&#x60;. (optional, default to json)
   * @param temperature The sampling temperature, between 0 and 1. Higher values like 0.8 will make
   *   the output more random, while lower values like 0.2 will make it more focused and
   *   deterministic. If set to 0, the model will use
   *   [log probability](https://en.wikipedia.org/wiki/Log_probability) to automatically increase
   *   the temperature until certain thresholds are hit. (optional, default to 0)
   * @param timestampGranularities The timestamp granularities to populate for this transcription.
   *   Any of these options: &#x60;word&#x60;, or &#x60;segment&#x60;. Note: There is no additional
   *   latency for segment timestamps, but generating word timestamps incurs additional latency.
   *   (optional, default to segment)
   * @return CreateTranscriptionResponse
   */
  @Suppress("UNCHECKED_CAST")
  open suspend fun createTranscription(
    file: com.xebia.functional.openai.apis.UploadFile,
    model: CreateTranscriptionRequestModel,
    language: kotlin.String? = null,
    prompt: kotlin.String? = null,
    responseFormat: ResponseFormatCreateTranscription? = ResponseFormatCreateTranscription.json,
    temperature: kotlin.Double? = 0.toDouble(),
    timestampGranularities: kotlin.collections.List<TimestampGranularitiesCreateTranscription>? =
      TimestampGranularitiesCreateTranscription.segment.asListOfOne()
  ): HttpResponse<CreateTranscriptionResponse> {

    val localVariableAuthNames = listOf<String>("ApiKeyAuth")

    val localVariableBody = formData {
      file?.apply { appendGen("file", file) }
      model?.apply { appendGen("model", model) }
      language?.apply { appendGen("language", language) }
      prompt?.apply { appendGen("prompt", prompt) }
      responseFormat?.apply { appendGen("response_format", responseFormat) }
      temperature?.apply { appendGen("temperature", temperature) }
      timestampGranularities?.onEach { appendGen("timestamp_granularities[][]", it) }
    }

    val localVariableQuery = mutableMapOf<String, List<String>>()
    val localVariableHeaders = mutableMapOf<String, String>()

    val localVariableConfig =
      RequestConfig<kotlin.Any?>(
        RequestMethod.POST,
        "/audio/transcriptions",
        query = localVariableQuery,
        headers = localVariableHeaders,
        requiresAuthentication = true,
      )

    return multipartFormRequest(localVariableConfig, localVariableBody, localVariableAuthNames)
      .wrap()
  }

  /**
   * Translates audio into English.
   *
   * @param file The audio file object (not file name) translate, in one of these formats: flac,
   *   mp3, mp4, mpeg, mpga, m4a, ogg, wav, or webm.
   * @param model
   * @param prompt An optional text to guide the model&#39;s style or continue a previous audio
   *   segment. The [prompt](/docs/guides/speech-to-text/prompting) should be in English. (optional)
   * @param responseFormat The format of the transcript output, in one of these options:
   *   &#x60;json&#x60;, &#x60;text&#x60;, &#x60;srt&#x60;, &#x60;verbose_json&#x60;, or
   *   &#x60;vtt&#x60;. (optional, default to "json")
   * @param temperature The sampling temperature, between 0 and 1. Higher values like 0.8 will make
   *   the output more random, while lower values like 0.2 will make it more focused and
   *   deterministic. If set to 0, the model will use
   *   [log probability](https://en.wikipedia.org/wiki/Log_probability) to automatically increase
   *   the temperature until certain thresholds are hit. (optional, default to 0)
   * @return CreateTranslationResponse
   */
  @Suppress("UNCHECKED_CAST")
  open suspend fun createTranslation(
    file: com.xebia.functional.openai.apis.UploadFile,
    model: CreateTranscriptionRequestModel,
    prompt: kotlin.String? = null,
    responseFormat: kotlin.String? = "json",
    temperature: kotlin.Double? = 0.toDouble()
  ): HttpResponse<CreateTranslationResponse> {

    val localVariableAuthNames = listOf<String>("ApiKeyAuth")

    val localVariableBody = formData {
      file?.apply { appendGen("file", file) }
      model?.apply { appendGen("model", model) }
      prompt?.apply { appendGen("prompt", prompt) }
      responseFormat?.apply { appendGen("response_format", responseFormat) }
      temperature?.apply { appendGen("temperature", temperature) }
    }

    val localVariableQuery = mutableMapOf<String, List<String>>()
    val localVariableHeaders = mutableMapOf<String, String>()

    val localVariableConfig =
      RequestConfig<kotlin.Any?>(
        RequestMethod.POST,
        "/audio/translations",
        query = localVariableQuery,
        headers = localVariableHeaders,
        requiresAuthentication = true,
      )

    return multipartFormRequest(localVariableConfig, localVariableBody, localVariableAuthNames)
      .wrap()
  }
}
