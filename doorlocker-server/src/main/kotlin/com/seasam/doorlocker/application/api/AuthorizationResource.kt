package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.AccessDto
import com.seasam.doorlocker.application.api.dto.AuthorizationRequestDto
import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.application.api.ext.forbidden
import com.seasam.doorlocker.application.api.ext.ok
import com.seasam.doorlocker.domain.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.PublicKey
import reactor.core.publisher.Mono
import java.util.*
import javax.crypto.Cipher

@Validated
@RestController
@RequestMapping("/api/auth")
class AuthorizationResource(private val authorizationService: AuthorizationService) {

    @GetMapping("/challenges/{deviceKey}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getChallenge(@RequestParam deviceKey: PublicKey) = authorizationService.generateChallenge(deviceKey)

    @PostMapping("/solutions", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun authorize(authorizationRequest: AuthorizationRequestDto): Mono<ResponseEntity<AccessDto>> {
        val (deviceKey, thingId, nonce) = authorizationRequest
        return authorizationService.authorize(deviceKey, thingId, nonce)
            .map { it.asDto() }
            .map { ok(it) }
            .defaultIfEmpty(forbidden())
    }
}
