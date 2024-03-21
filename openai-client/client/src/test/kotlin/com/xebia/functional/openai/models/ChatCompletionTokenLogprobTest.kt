/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.models

import io.kotlintest.specs.ShouldSpec

class ChatCompletionTokenLogprobTest : ShouldSpec() {
  init {
    // uncomment below to create an instance of ChatCompletionTokenLogprob
    // val modelInstance = ChatCompletionTokenLogprob()

    // to test the property `token` - The token.
    should("test token") {
      // uncomment below to test the property
      // modelInstance.token shouldBe ("TODO")
    }

    // to test the property `logprob` - The log probability of this token.
    should("test logprob") {
      // uncomment below to test the property
      // modelInstance.logprob shouldBe ("TODO")
    }

    // to test the property `bytes` - A list of integers representing the UTF-8 bytes representation
    // of the token. Useful in instances where characters are represented by multiple tokens and
    // their byte representations must be combined to generate the correct text representation. Can
    // be `null` if there is no bytes representation for the token.
    should("test bytes") {
      // uncomment below to test the property
      // modelInstance.bytes shouldBe ("TODO")
    }

    // to test the property `topLogprobs` - List of the most likely tokens and their log
    // probability, at this token position. In rare cases, there may be fewer than the number of
    // requested `top_logprobs` returned.
    should("test topLogprobs") {
      // uncomment below to test the property
      // modelInstance.topLogprobs shouldBe ("TODO")
    }
  }
}