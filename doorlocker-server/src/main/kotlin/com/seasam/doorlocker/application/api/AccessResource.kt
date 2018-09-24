package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.DeviceDto
import com.seasam.doorlocker.domain.ThingId
import com.seasam.doorlocker.domain.UserId
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Validated
@RestController
@RequestMapping("/api/accesses")
class AccessResource {

    @GetMapping("/accesses", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllAccesses(@RequestParam(required = false) thingId: ThingId,
                       @RequestParam(required = false) userId: UserId,
                       @RequestParam(required = false) period: String) =
        Mono.just(ResponseEntity<DeviceDto>(HttpStatus.NOT_IMPLEMENTED)) // TODO: Flux<DeviceDto> HttpStatus.OK
}
