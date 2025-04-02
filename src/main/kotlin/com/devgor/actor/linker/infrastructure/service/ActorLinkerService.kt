package com.devgor.actor.linker.infrastructure.service

import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore

class ActorLinkerService(private val vectorStore: VectorStore) {
    fun addActor(name: String, movies: List<String>) {
        val movie = movies.joinToString(separator = "\n")
        val document = Document(movie, mapOf("actor" to name))
        vectorStore.add(listOf(document))
    }

    fun findSimilarActors(actorName: String): List<String> {
        val request = SearchRequest.builder().query(actorName).topK(3).build()
        val documents = vectorStore.similaritySearch(request) ?: return emptyList()
        return documents.map { it.metadata["actor"] as String }
    }
}


