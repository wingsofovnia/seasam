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
@RequestMapping("/api/things")
class ThingPermissionResource {

    @PostMapping("/{thingId}/permissions/{userId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createThingPermission(@PathVariable thingId: ThingId, @PathVariable userId: UserId) =
        Mono.just(ResponseEntity<PermissionDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<DeviceDto>> HttpStatus.CREATED


    @GetMapping("/{thingId}/permissions", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllThingPermissions(@PathVariable thingId: ThingId) =
        Mono.just(ResponseEntity<PermissionDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Flux<PermissionDto> HttpStatus.OK


    @DeleteMapping("/{thingId}/permissions/{userId}")
    fun deleteThingPermission(@PathVariable thingId: ThingId, @PathVariable userId: UserId) =
        Mono.just(ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<Void>> HttpStatus.NO_CONTENT
}
