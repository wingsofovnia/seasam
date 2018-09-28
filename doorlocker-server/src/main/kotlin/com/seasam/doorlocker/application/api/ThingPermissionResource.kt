package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.PermissionDto
import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.application.api.ext.created
import com.seasam.doorlocker.application.api.ext.noContent
import com.seasam.doorlocker.application.api.ext.notFound
import com.seasam.doorlocker.application.api.ext.ok
import com.seasam.doorlocker.domain.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Validated
@RestController
@RequestMapping("/api/things")
class ThingPermissionResource(
    val thingRepository: ThingRepository,
    val userRepository: UserRepository) {

    @PostMapping("/{thingId}/permissions/{userId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createThingPermission(@PathVariable thingId: ThingId,
                              @PathVariable userId: UserId): Mono<ResponseEntity<PermissionDto>> {
        val permission = Permission(userId)

        return userRepository.existsActiveById(userId)
            .filter { it }
            .flatMap { thingRepository.findById(thingId) }
            .doOnNext { it.addPermission(permission) }
            .flatMap { thingRepository.save(it) }
            .map { created(permission.asDto()) }
            .defaultIfEmpty(notFound())
    }

    @GetMapping("/{thingId}/permissions", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllThingPermissions(@PathVariable thingId: ThingId) =
        thingRepository.findById(thingId)
            .map { it.permissions }
            .map { it.map(Permission::asDto) }
            .map { ok(Flux.fromIterable(it)) }
            .defaultIfEmpty(notFound())


    @DeleteMapping("/{thingId}/permissions/{userId}")
    fun deleteThingPermission(@PathVariable thingId: ThingId, @PathVariable userId: UserId) =
        thingRepository.findById(thingId)
            .filter { it.hasPermission(userId) }
            .doOnNext { it.removePermission(userId) }
            .flatMap { thingRepository.save(it) }
            .map { noContent() }
            .defaultIfEmpty(notFound())
}
