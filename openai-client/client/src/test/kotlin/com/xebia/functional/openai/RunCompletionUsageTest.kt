/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai

import io.kotlintest.specs.ShouldSpec

class RunCompletionUsageTest : ShouldSpec() {
  init {
    // uncomment below to create an instance of RunCompletionUsage
    // val modelInstance = RunCompletionUsage()

    // to test the property `completionTokens` - Number of completion tokens used over the course of
    // the run.
    should("test completionTokens") {
      // uncomment below to test the property
      // modelInstance.completionTokens shouldBe ("TODO")
    }

    // to test the property `promptTokens` - Number of prompt tokens used over the course of the
    // run.
    should("test promptTokens") {
      // uncomment below to test the property
      // modelInstance.promptTokens shouldBe ("TODO")
    }

    // to test the property `totalTokens` - Total number of tokens used (prompt + completion).
    should("test totalTokens") {
      // uncomment below to test the property
      // modelInstance.totalTokens shouldBe ("TODO")
    }
  }
}