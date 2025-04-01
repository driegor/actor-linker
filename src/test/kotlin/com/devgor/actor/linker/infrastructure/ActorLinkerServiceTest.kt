package com.devgor.actor.linker.infrastructure

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.anyList
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.ai.vectorstore.VectorStore
import org.mockito.kotlin.whenever
import org.springframework.ai.document.Document
import kotlin.test.assertEquals

class ActorLinkerServiceTest {

    private lateinit var vectorStore: VectorStore
    private lateinit var actorLinkerService: ActorLinkerService

    @BeforeEach
    fun setUp() {
        vectorStore = mock()
        actorLinkerService = ActorLinkerService()
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
        val expectedActors = listOf("Tom Hardy", "Kate Winslet").map { Document(it) }
        whenever(vectorStore.similaritySearch(actorName)).thenReturn(expectedActors)
        val similarActors = actorLinkerService.findSimilarActors(actorName)
        assertEquals(expectedActors, similarActors)
    }
}