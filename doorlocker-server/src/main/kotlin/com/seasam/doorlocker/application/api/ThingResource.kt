package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.ThingDto
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
    fun create(@RequestBody dto: ThingDto) =
        repository.save(dto.asThing())
            .map { ThingDto.from(it) }
            .map { ResponseEntity(it, HttpStatus.CREATED) }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun readOne(@PathVariable id: ThingId) =
        repository.findById(id)
            .map { ThingDto.from(it) }
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun readAll() = repository.findAll().map { ThingDto.from(it) }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: ThingId) = repository.deleteById(id)
}
