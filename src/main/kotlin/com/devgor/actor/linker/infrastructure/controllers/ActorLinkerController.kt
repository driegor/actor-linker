package com.devgor.actor.linker.infrastructure.controllers

import com.devgor.actor.linker.infrastructure.service.ActorLinkerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/actors")
class ActorLinkerController(private val actorLinkerService: ActorLinkerService) {

    @PostMapping
    fun addActor(@RequestBody actorRequest: ActorRequest) {
        actorLinkerService.addActor(actorRequest.name, actorRequest.movies)
    }

    @GetMapping("/{name}/similar")
    fun findSimilarActors(@PathVariable name: String): List<String> {
        return actorLinkerService.findSimilarActors(name)
    }
}