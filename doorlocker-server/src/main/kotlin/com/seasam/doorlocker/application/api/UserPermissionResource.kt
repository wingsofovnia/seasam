package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.PermissionDto
import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.application.api.ext.noContent
import com.seasam.doorlocker.application.api.ext.notFound
import com.seasam.doorlocker.application.api.ext.ok
import com.seasam.doorlocker.domain.ThingId
import com.seasam.doorlocker.domain.UserId
import com.seasam.doorlocker.domain.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@Validated
@RestController
@RequestMapping("/api/users")
class UserPermissionResource(val repository: UserRepository) {

    @PostMapping("/{userId}/permissions/{thingId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createUserPermission(@PathVariable userId: UserId, @PathVariable thingId: ThingId) =
        Mono.just(ResponseEntity<PermissionDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<DeviceDto>> HttpStatus.CREATED


    @GetMapping("/{userId}/permissions", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUserPermissions(@PathVariable userId: UserId, @RequestParam(required = false) thingId: ThingId) =
        repository.findActiveById(userId)
            .map { it.allPermissions }
            .map { it.map { it.asDto() } }
            .map { ok(it) }
            .defaultIfEmpty(notFound())

    @DeleteMapping("/{userId}/permissions/{thingId}")
    fun deleteUserPermission(@PathVariable userId: UserId, @PathVariable thingId: ThingId) =
        repository.findActiveById(userId)
            .filter { it.hasPermission(thingId)}
            .doOnNext { it.removePermission(thingId) }
            .flatMap { repository.save(it) }
            .map { noContent() }
            .defaultIfEmpty(notFound())

}
