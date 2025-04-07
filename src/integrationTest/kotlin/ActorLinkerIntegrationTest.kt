package com.example.actorlinker

import com.devgor.actor.linker.ActorLinkerApplication
import com.example.actorlinker.com.example.actorlinker.infrastructure.helper.DockerComposeHelper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(classes = [ActorLinkerApplication::class])
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
@Testcontainers
class ActorLinkerIntegrationTest {

    companion object {
        @Container private val dockerComposeContainer = DockerComposeHelper.create()
    }

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should add actor and find similar`() {
        val requestBody = """
            {
                "name": "Tom Hanks",
                "movies": ["Forrest Gump", "Cast Away", "The Terminal"]
            }
        """.trimIndent()

        mockMvc.post("/actors") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
        }.andExpect {
            status { isOk() }
        }

        mockMvc.get("/actors/Tom Hanks/similar")
            .andExpect {
                status { isOk() }
                content { json("[]") }
            }
    }
}
