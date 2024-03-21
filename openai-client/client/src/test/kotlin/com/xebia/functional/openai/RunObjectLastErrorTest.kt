/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai

import io.kotlintest.specs.ShouldSpec

class RunObjectLastErrorTest : ShouldSpec() {
  init {
    // uncomment below to create an instance of RunObjectLastError
    // val modelInstance = RunObjectLastError()

    // to test the property `code` - One of `server_error` or `rate_limit_exceeded`.
    should("test code") {
      // uncomment below to test the property
      // modelInstance.code shouldBe ("TODO")
    }

    // to test the property `message` - A human-readable description of the error.
    should("test message") {
      // uncomment below to test the property
      // modelInstance.message shouldBe ("TODO")
    }
  }
}