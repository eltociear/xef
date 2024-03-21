/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package org.openapitools.client.apis

import io.kotlintest.specs.ShouldSpec

class FineTuningApiTest : ShouldSpec() {
  init {
    // uncomment below to create an instance of FineTuningApi
    // val apiInstance = FineTuningApi()

    // to test cancelFineTuningJob
    should("test cancelFineTuningJob") {
      // uncomment below to test cancelFineTuningJob
      // val fineTuningJobId : kotlin.String = ft-AF1WoRqd3aJAHsqc9NY7iL8F // kotlin.String | The ID
      // of the fine-tuning job to cancel.
      // val result : FineTuningJob = apiInstance.cancelFineTuningJob(fineTuningJobId)
      // result shouldBe ("TODO")
    }

    // to test createFineTuningJob
    should("test createFineTuningJob") {
      // uncomment below to test createFineTuningJob
      // val createFineTuningJobRequest : CreateFineTuningJobRequest =  //
      // CreateFineTuningJobRequest |
      // val result : FineTuningJob = apiInstance.createFineTuningJob(createFineTuningJobRequest)
      // result shouldBe ("TODO")
    }

    // to test listFineTuningEvents
    should("test listFineTuningEvents") {
      // uncomment below to test listFineTuningEvents
      // val fineTuningJobId : kotlin.String = ft-AF1WoRqd3aJAHsqc9NY7iL8F // kotlin.String | The ID
      // of the fine-tuning job to get events for.
      // val after : kotlin.String = after_example // kotlin.String | Identifier for the last event
      // from the previous pagination request.
      // val limit : kotlin.Int = 56 // kotlin.Int | Number of events to retrieve.
      // val result : ListFineTuningJobEventsResponse =
      // apiInstance.listFineTuningEvents(fineTuningJobId, after, limit)
      // result shouldBe ("TODO")
    }

    // to test listPaginatedFineTuningJobs
    should("test listPaginatedFineTuningJobs") {
      // uncomment below to test listPaginatedFineTuningJobs
      // val after : kotlin.String = after_example // kotlin.String | Identifier for the last job
      // from the previous pagination request.
      // val limit : kotlin.Int = 56 // kotlin.Int | Number of fine-tuning jobs to retrieve.
      // val result : ListPaginatedFineTuningJobsResponse =
      // apiInstance.listPaginatedFineTuningJobs(after, limit)
      // result shouldBe ("TODO")
    }

    // to test retrieveFineTuningJob
    should("test retrieveFineTuningJob") {
      // uncomment below to test retrieveFineTuningJob
      // val fineTuningJobId : kotlin.String = ft-AF1WoRqd3aJAHsqc9NY7iL8F // kotlin.String | The ID
      // of the fine-tuning job.
      // val result : FineTuningJob = apiInstance.retrieveFineTuningJob(fineTuningJobId)
      // result shouldBe ("TODO")
    }
  }
}