package com.seasam.doorlocker.server.controller

import com.seasam.doorlocker.server.domain.User
import com.seasam.doorlocker.server.repository.UserRepository
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class UserController(val userRepository: UserRepository) {

    @GetMapping(value = "/save", produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
    fun saveAndSend(@RequestParam("surname") surname: String) =
        userRepository
            .save(User(UUID.randomUUID().toString(), surname, "Test"))
            .flux()

}
