package com.seasam.doorlocker.application.api

import com.seasam.doorlocker.application.api.dto.AccessDto
import com.seasam.doorlocker.application.api.dto.AuthorizationRequestDto
import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.application.api.ext.forbidden
import com.seasam.doorlocker.application.api.ext.ok
import com.seasam.doorlocker.application.api.ext.unauthorized
import com.seasam.doorlocker.domain.auth.AuthorizationService
import com.seasam.doorlocker.domain.auth.ChallengeService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.security.PublicKey
import java.util.*

@Validated
@RestController
@RequestMapping("/api/auth")
class AuthorizationResource(private val authorizationService: AuthorizationService,
                            private val challengeService: ChallengeService) {

    @GetMapping("/challenges/{deviceKey}", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getChallenge(@RequestParam deviceKey: PublicKey): String {
        val challengeBytes = challengeService.generateChallenge(deviceKey)
        return Base64.getEncoder().encodeToString(challengeBytes)
    }

    @PostMapping("/solutions", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun authorize(authorizationRequest: AuthorizationRequestDto): Mono<ResponseEntity<AccessDto>> {
        val (deviceKey, thingId, solutionBase64) = authorizationRequest
        val solution = Base64.getDecoder().decode(solutionBase64)

        try {
            if (!challengeService.checkSolution(deviceKey, solution))
                return Mono.just(unauthorized())
        } catch (e: Exception) {
            //TODO: log, improve error response
            return Mono.just(unauthorized())
        }

        return authorizationService.authorize(deviceKey, thingId)
            .map { it.asDto() }
            .map { ok(it) }
            .defaultIfEmpty(forbidden())
    }
}
