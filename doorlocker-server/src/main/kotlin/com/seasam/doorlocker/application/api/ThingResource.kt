package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.ThingDto
import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.domain.Thing
import com.seasam.doorlocker.domain.ThingId
import com.seasam.doorlocker.domain.ThingRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/things")
class ThingResource(val repository: ThingRepository) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createThing(@RequestBody dto: ThingDto) =
        repository.save(dto.asThing())
            .map(Thing::asDto)
            .map { ResponseEntity(it, HttpStatus.CREATED) }

    @GetMapping("/{thingId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOneThing(@PathVariable thingId: ThingId) =
        repository.findById(thingId)
            .map(Thing::asDto)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllThings() = repository.findAll().map(Thing::asDto)

    @PutMapping("/{thingId}", consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateThing(@PathVariable thingId: ThingId, @RequestBody dto: ThingDto) =
        repository.save(dto.apply { id = thingId }.asThing())
            .map(Thing::asDto)
            .map { ResponseEntity(it, HttpStatus.OK) }

    @DeleteMapping("/{thingId}")
    fun deleteThing(@PathVariable thingId: ThingId) =
        repository.deleteById(thingId)
            .map { ResponseEntity.status(HttpStatus.NO_CONTENT).build<Void>() }
}
