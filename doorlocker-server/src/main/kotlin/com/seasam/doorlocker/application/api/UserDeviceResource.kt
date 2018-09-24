package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.DeviceDto
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
class UserDeviceResource {


    @PostMapping("/{userId}/devices", produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUserDevice(@PathVariable userId: UserId, @RequestBody dto: DeviceDto) =
        Mono.just(ResponseEntity<DeviceDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<DeviceDto>> HttpStatus.CREATED


    @GetMapping("/{userId}/devices/{deviceKey}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOneUserDevice(@PathVariable userId: UserId, @PathVariable dto: String) =
        Mono.just(ResponseEntity<DeviceDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<DeviceDto>> HttpStatus.OK


    @GetMapping("/{userId}/devices", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUserDevices(@PathVariable userId: UserId) =
        Mono.just(ResponseEntity<DeviceDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Flux<DeviceDto> HttpStatus.OK


    @DeleteMapping("/{userId}/devices/{deviceKey}")
    fun deleteUserDevice(@PathVariable userId: UserId, @PathVariable deviceKey: String) =
        Mono.just(ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<Void>> HttpStatus.NO_CONTENT
}
