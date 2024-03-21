/**
 * Please note: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 */
@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.xebia.functional.openai.models

import io.kotlintest.specs.ShouldSpec

class EmbeddingTest : ShouldSpec() {
  init {
    // uncomment below to create an instance of Embedding
    // val modelInstance = Embedding()

    // to test the property `index` - The index of the embedding in the list of embeddings.
    should("test index") {
      // uncomment below to test the property
      // modelInstance.index shouldBe ("TODO")
    }

    // to test the property `embedding` - The embedding vector, which is a list of floats. The
    // length of vector depends on the model as listed in the [embedding
    // guide](/docs/guides/embeddings).
    should("test embedding") {
      // uncomment below to test the property
      // modelInstance.embedding shouldBe ("TODO")
    }

    // to test the property ``object`` - The object type, which is always \"embedding\".
    should("test `object`") {
      // uncomment below to test the property
      // modelInstance.`object` shouldBe ("TODO")
    }
  }
}