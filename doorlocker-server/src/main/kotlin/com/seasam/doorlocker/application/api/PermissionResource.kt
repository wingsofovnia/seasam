package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.PermissionDto
import com.seasam.doorlocker.domain.ThingId
import com.seasam.doorlocker.domain.UserId
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@Validated
@RestController
@RequestMapping("/api/users")
class PermissionResource {

    @PostMapping("/{userId}/permissions/{thingId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createUserPermission(@PathVariable userId: UserId, @PathVariable thingId: ThingId) =
        Mono.just(ResponseEntity<PermissionDto>(HttpStatus.NOT_IMPLEMENTED)) // Mono<ResponseEntity<DeviceDto>> HttpStatus.CREATED


    @GetMapping("/{userId}/permissions", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUserPermissions(@PathVariable userId: UserId, @RequestParam(required = false) thingId: ThingId) =
        Mono.just(ResponseEntity<PermissionDto>(HttpStatus.NOT_IMPLEMENTED)) // Flux<PermissionDto> HttpStatus.OK


    @DeleteMapping("/{userId}/permissions/{thingId}")
    fun deleteUserPermission(@PathVariable userId: UserId, @PathVariable thingId: ThingId) =
        Mono.just(ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED)) // Mono<ResponseEntity<Void>> HttpStatus.NO_CONTENT
}
