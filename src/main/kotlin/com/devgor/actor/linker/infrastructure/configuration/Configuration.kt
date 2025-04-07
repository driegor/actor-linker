package com.devgor.actor.linker.infrastructure.configuration

import com.devgor.actor.linker.infrastructure.controllers.ActorLinkerController
import com.devgor.actor.linker.infrastructure.service.ActorLinkerService
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Configuration {

    @Bean
    fun actorLinkerService(vectorStore: VectorStore): ActorLinkerService {
        return ActorLinkerService(vectorStore)
    }

    @Bean
    fun actorLinkerController(actorLinkerService: ActorLinkerService): ActorLinkerController {
        return ActorLinkerController(actorLinkerService)
    }
}