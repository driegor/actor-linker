package com.example.actorlinker.com.example.actorlinker.infrastructure.helper


import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitAllStrategy
import java.io.File

class DockerComposeHelper {

    companion object {
        private const val POSTGRESQL = "postgres"
        private const val POSTGRESQL_PORT = 5432
        private const val OLLAMA = "ollama"
        private const val OLLAMAL_PORT = 8080


        fun create(): ComposeContainer {
            return ComposeContainer(File("docker-compose.yml"))
                .apply { withLocalCompose(true) }
                .apply {
                    withExposedService(
                        POSTGRESQL,
                        POSTGRESQL_PORT,
                        WaitAllStrategy(WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY).apply {
                            withStrategy(Wait.forListeningPort())
                        })
                }.apply {
                    withExposedService(
                        OLLAMA,
                        OLLAMAL_PORT,
                        WaitAllStrategy(WaitAllStrategy.Mode.WITH_INDIVIDUAL_TIMEOUTS_ONLY).apply {
                            withStrategy(Wait.forListeningPort())
                        })
                }
        }
    }
}

