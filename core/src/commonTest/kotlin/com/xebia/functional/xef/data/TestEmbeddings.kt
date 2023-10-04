package com.xebia.functional.xef.data

import com.xebia.functional.xef.llm.Embeddings
import com.xebia.functional.xef.llm.models.ModelID
import com.xebia.functional.xef.llm.models.embeddings.Embedding
import com.xebia.functional.xef.llm.models.embeddings.EmbeddingRequest
import com.xebia.functional.xef.llm.models.embeddings.EmbeddingResult
import com.xebia.functional.xef.llm.models.embeddings.RequestConfig
import com.xebia.functional.xef.llm.models.usage.Usage

class TestEmbeddings : Embeddings {

  override val modelID = ModelID("test-embeddings")

  override fun copy(modelID: ModelID) = TestEmbeddings()

  override suspend fun embedDocuments(
    texts: List<String>,
    requestConfig: RequestConfig,
    chunkSize: Int?
  ): List<Embedding> = emptyList()

  override suspend fun embedQuery(text: String, requestConfig: RequestConfig): List<Embedding> =
    emptyList()

  override suspend fun createEmbeddings(request: EmbeddingRequest): EmbeddingResult =
    EmbeddingResult(emptyList(), Usage.ZERO)
}
