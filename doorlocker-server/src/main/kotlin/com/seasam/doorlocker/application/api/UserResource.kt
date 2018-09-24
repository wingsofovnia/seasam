package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.UserDto
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
class UserResource {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@RequestBody dto: UserDto) =
        Mono.just(ResponseEntity<UserDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<UserDto>> HttpStatus.CREATED


    @GetMapping("/{userId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOneUser(@PathVariable userId: UserId) =
        Mono.just(ResponseEntity<UserDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<UserDto>> HttpStatus.OK

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUsers() =
        Mono.just(ResponseEntity<UserDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Flux<UserDto> HttpStatus.OK

    @PutMapping("/{userId}", consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUser(@PathVariable userId: UserId, @RequestBody dto: UserDto) =
        Mono.just(ResponseEntity<UserDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<UserDto>> HttpStatus.OK


    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UserId) =
        Mono.just(ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Mono<ResponseEntity<Void>> HttpStatus.NO_CONTENT


}
