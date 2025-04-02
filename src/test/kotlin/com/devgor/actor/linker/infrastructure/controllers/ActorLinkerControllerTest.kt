package com.devgor.actor.linker.infrastructure.controllers

import com.devgor.actor.linker.infrastructure.service.ActorLinkerService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class ActorLinkerControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var actorLinkerService: ActorLinkerService
    private lateinit var actorLinkerController: ActorLinkerController

    @BeforeEach
    fun setup() {
        actorLinkerService = mock(ActorLinkerService::class.java)
        actorLinkerController = ActorLinkerController(actorLinkerService)
        mockMvc = MockMvcBuilders.standaloneSetup(actorLinkerController).build()
    }

    @Test
    fun `add actor with valid request returns OK`() {
        val actorRequest = """{"name": "John Doe", "movies": ["Movie1", "Movie2"]}"""

        mockMvc.perform(
            post("/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(actorRequest)
        )
            .andExpect(status().isOk)

        verify(actorLinkerService).addActor("John Doe", listOf("Movie1", "Movie2"))
    }

    @Test
    fun `add actor with empty name returns bad request`() {
        val actorRequest = """{"movies": ["Movie1", "Movie2"]}"""

        mockMvc.perform(
            post("/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(actorRequest)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `find similar actors with valid name returns list of actors`() {
        val similarActors = listOf("Actor1", "Actor2")
        `when`(actorLinkerService.findSimilarActors("John Doe")).thenReturn(similarActors)

        mockMvc.perform(get("/actors/John Doe/similar"))
            .andExpect(status().isOk)
            .andExpect(content().json("""["Actor1", "Actor2"]"""))

        verify(actorLinkerService).findSimilarActors("John Doe")
    }

    @Test
    fun `find similar actors with non existent name returns empty list`() {
        `when`(actorLinkerService.findSimilarActors("NonExistent")).thenReturn(emptyList())

        mockMvc.perform(get("/actors/NonExistent/similar"))
            .andExpect(status().isOk)
            .andExpect(content().json("""[]"""))

        verify(actorLinkerService).findSimilarActors("NonExistent")
    }
}