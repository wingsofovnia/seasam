package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.UserDto
import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.domain.User
import com.seasam.doorlocker.domain.UserId
import com.seasam.doorlocker.domain.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/users")
class UserResource(val repository: UserRepository) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(@RequestBody dto: UserDto) =
        repository.save(dto.asUser())
            .map(User::asDto)
            .map { ResponseEntity(it, HttpStatus.CREATED) }

    @GetMapping("/{userId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOneUser(@PathVariable userId: UserId) =
        repository.findActiveById(userId)
            .map(User::asDto)
            .map { ResponseEntity.ok(it) }
            .defaultIfEmpty(ResponseEntity.notFound().build())

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUsers() = repository.findAllActive().map(User::asDto)

    @PutMapping("/{userId}", consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUser(@PathVariable userId: UserId, @RequestBody dto: UserDto) =
        repository.save(dto.apply { id = userId }.asUser())
            .map(User::asDto)
            .map { ResponseEntity(it, HttpStatus.OK) }


    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UserId) =
        repository.deactivateUser(userId)
            .map { ResponseEntity.status(HttpStatus.NO_CONTENT).build<Void>() }
}
