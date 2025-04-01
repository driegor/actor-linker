package com.devgor.actor.linker.infrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore

class ActorLinkerServiceTest {

    private lateinit var vectorStore: VectorStore
    private lateinit var actorLinkerService: ActorLinkerService

    @BeforeEach
    fun setUp() {
        vectorStore = mock()
        actorLinkerService = ActorLinkerService(vectorStore)
    }

    @Test
    fun `should add actor and generate embedding`() {
        val name = "Leonardo DiCaprio"
        val movies = listOf("Inception", "Titanic", "The Revenant")
        actorLinkerService.addActor(name, movies)
        verify(vectorStore, times(1)).add(anyList())
    }

    @Test
    fun `should find similar actors`() {
        val actorName = "Leonardo DiCaprio"
        val expectedList =
            listOf("Tom Hardy", "Kate Winslet").map { Document("anyEmbeddingText", mapOf("actor" to it)) }
        whenever(
            vectorStore.similaritySearch(any(SearchRequest::class.java))
        ).thenReturn(expectedList)
        val similarActors = actorLinkerService.findSimilarActors(actorName)
        assertEquals(expectedList.map { it.metadata["actor"] }, similarActors)
    }
}