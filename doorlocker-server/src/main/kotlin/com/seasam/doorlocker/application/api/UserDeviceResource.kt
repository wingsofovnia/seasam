package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.DeviceDto
import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.application.api.ext.created
import com.seasam.doorlocker.application.api.ext.noContent
import com.seasam.doorlocker.application.api.ext.notFound
import com.seasam.doorlocker.application.api.ext.ok
import com.seasam.doorlocker.domain.UserId
import com.seasam.doorlocker.domain.UserRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.security.PublicKey

@Validated
@RestController
@RequestMapping("/api/users")
class UserDeviceResource(val repository: UserRepository) {

    @PostMapping("/{userId}/devices",
        produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUserDevice(@PathVariable userId: UserId, @RequestBody dto: DeviceDto): Mono<ResponseEntity<DeviceDto>> {
        val device = dto.asDevice()

        return repository.findActiveById(userId)
            .doOnNext { it.addDevice(device) }
            .flatMap { repository.save(it) }
            .map { created(dto, UserDeviceResource::getOneUserDevice, userId, device.key) }
            .defaultIfEmpty(notFound())
    }

    @GetMapping("/{userId}/devices/{deviceKey}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOneUserDevice(@PathVariable userId: UserId, @PathVariable deviceKey: PublicKey) =
        repository.findActiveById(userId)
            .flatMap { it.findDevice(deviceKey)?.toMono() }
            .map { it.asDto() }
            .map { ok(it) }
            .defaultIfEmpty(notFound())

    @GetMapping("/{userId}/devices", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUserDevices(@PathVariable userId: UserId) =
        repository.findActiveById(userId)
            .map { it.allDevices }
            .map { it.map { it.asDto() } } // no flatMapIterable s.t. it won't defaultIfEmpty for users with no devices
            .map { ok(it) }
            .defaultIfEmpty(notFound())

    @DeleteMapping("/{userId}/devices/{deviceKey}")
    fun deleteUserDevice(@PathVariable userId: UserId, @PathVariable deviceKey: PublicKey) =
        repository.findActiveById(userId)
            .filter { it.hasDevice(key = deviceKey) }
            .doOnNext { it.removeDevice(deviceKey) }
            .flatMap { repository.save(it) }
            .map { noContent() }
            .defaultIfEmpty(notFound())
}
