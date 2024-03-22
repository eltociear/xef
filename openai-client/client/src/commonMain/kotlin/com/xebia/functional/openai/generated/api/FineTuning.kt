/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.generated.api

import com.xebia.functional.openai.generated.api.FineTuning.*
import com.xebia.functional.openai.generated.model.CreateFineTuningJobRequest
import com.xebia.functional.openai.generated.model.FineTuningJob
import com.xebia.functional.openai.generated.model.ListFineTuningJobEventsResponse
import com.xebia.functional.openai.generated.model.ListPaginatedFineTuningJobsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.Json

/**  */
interface FineTuning {

  /**
   * Immediately cancel a fine-tune job.
   *
   * @param fineTuningJobId The ID of the fine-tuning job to cancel.
   * @return FineTuningJob
   */
  suspend fun cancelFineTuningJob(fineTuningJobId: kotlin.String): FineTuningJob

  /**
   * Creates a fine-tuning job which begins the process of creating a new model from a given
   * dataset. Response includes details of the enqueued job including job status and the name of the
   * fine-tuned models once complete. [Learn more about fine-tuning](/docs/guides/fine-tuning)
   *
   * @param createFineTuningJobRequest
   * @return FineTuningJob
   */
  suspend fun createFineTuningJob(
    createFineTuningJobRequest: CreateFineTuningJobRequest
  ): FineTuningJob

  /**
   * Get status updates for a fine-tuning job.
   *
   * @param fineTuningJobId The ID of the fine-tuning job to get events for.
   * @param after Identifier for the last event from the previous pagination request. (optional)
   * @param limit Number of events to retrieve. (optional, default to 20)
   * @return ListFineTuningJobEventsResponse
   */
  suspend fun listFineTuningEvents(
    fineTuningJobId: kotlin.String,
    after: kotlin.String? = null,
    limit: kotlin.Int? = 20
  ): ListFineTuningJobEventsResponse

  /**
   * List your organization&#39;s fine-tuning jobs
   *
   * @param after Identifier for the last job from the previous pagination request. (optional)
   * @param limit Number of fine-tuning jobs to retrieve. (optional, default to 20)
   * @return ListPaginatedFineTuningJobsResponse
   */
  suspend fun listPaginatedFineTuningJobs(
    after: kotlin.String? = null,
    limit: kotlin.Int? = 20
  ): ListPaginatedFineTuningJobsResponse

  /**
   * Get info about a fine-tuning job. [Learn more about fine-tuning](/docs/guides/fine-tuning)
   *
   * @param fineTuningJobId The ID of the fine-tuning job.
   * @return FineTuningJob
   */
  suspend fun retrieveFineTuningJob(fineTuningJobId: kotlin.String): FineTuningJob
}

fun FineTuning(client: HttpClient): FineTuning =
  object : FineTuning {
    override suspend fun cancelFineTuningJob(
      fineTuningJobId: kotlin.String,
    ): FineTuningJob =
      client
        .request {
          method = HttpMethod.Post
          contentType(ContentType.Application.Json)
          url {
            path(
              "/fine_tuning/jobs/{fine_tuning_job_id}/cancel"
                .replace("{" + "fine_tuning_job_id" + "}", "$fineTuningJobId")
            )
          }
          setBody(io.ktor.client.utils.EmptyContent)
        }
        .body()

    override suspend fun createFineTuningJob(
      createFineTuningJobRequest: CreateFineTuningJobRequest,
    ): FineTuningJob =
      client
        .request {
          method = HttpMethod.Post
          contentType(ContentType.Application.Json)
          url { path("/fine_tuning/jobs") }
          setBody(createFineTuningJobRequest)
        }
        .body()

    override suspend fun listFineTuningEvents(
      fineTuningJobId: kotlin.String,
      after: kotlin.String?,
      limit: kotlin.Int?,
    ): ListFineTuningJobEventsResponse =
      client
        .request {
          method = HttpMethod.Get
          contentType(ContentType.Application.Json)
          parameter("after", listOf("$after"))
          parameter("limit", listOf("$limit"))
          url {
            path(
              "/fine_tuning/jobs/{fine_tuning_job_id}/events"
                .replace("{" + "fine_tuning_job_id" + "}", "$fineTuningJobId")
            )
          }
          setBody(io.ktor.client.utils.EmptyContent)
        }
        .body()

    override suspend fun listPaginatedFineTuningJobs(
      after: kotlin.String?,
      limit: kotlin.Int?,
    ): ListPaginatedFineTuningJobsResponse =
      client
        .request {
          method = HttpMethod.Get
          contentType(ContentType.Application.Json)
          parameter("after", listOf("$after"))
          parameter("limit", listOf("$limit"))
          url { path("/fine_tuning/jobs") }
          setBody(io.ktor.client.utils.EmptyContent)
        }
        .body()

    override suspend fun retrieveFineTuningJob(
      fineTuningJobId: kotlin.String,
    ): FineTuningJob =
      client
        .request {
          method = HttpMethod.Get
          contentType(ContentType.Application.Json)
          url {
            path(
              "/fine_tuning/jobs/{fine_tuning_job_id}"
                .replace("{" + "fine_tuning_job_id" + "}", "$fineTuningJobId")
            )
          }
          setBody(io.ktor.client.utils.EmptyContent)
        }
        .body()
  }