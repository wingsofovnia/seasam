package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.domain.Access
import com.seasam.doorlocker.domain.AccessRepository
import com.seasam.doorlocker.domain.ThingId
import com.seasam.doorlocker.domain.UserId
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/api/accesses")
class AccessResource(val repository: AccessRepository) {

    @GetMapping("/accesses", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllAccesses(@RequestParam(required = false) thingId: ThingId,
                       @RequestParam(required = false) userId: UserId,
                       @RequestParam(required = false) period: String) =
        repository.findAll().map(Access::asDto)
}
