package com.seasam.doorlocker.domain

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.security.PublicKey
import java.util.*
import javax.crypto.Cipher

@Service
class AuthorizationService(private val thingRepository: ThingRepository,
                           private val userRepository: UserRepository,
                           private val accessRepository: AccessRepository) {

    private val challenges = HashMap<PublicKey, String>()

    fun generateChallenge(deviceKey: PublicKey): String {
        val challenge = UUID.randomUUID().toString()
        challenges[deviceKey] = challenge
        return challenge
    }

    fun authorize(deviceKey: PublicKey, thingId: ThingId, nonce: String): Mono<Access> {
        if (!checkChallengeSolution(deviceKey, nonce)) {
            return Mono.empty()
        }

        return userRepository.findByDeviceKey(deviceKey).map { it.id }
            .flatMap { userId -> thingRepository.findById(thingId).map { it to userId } }
            .flatMapIterable { (thing, userId) -> thing.permissions.filter { it.userId == userId } }
            .next()
            .map { Access(thingId = thingId, permission = it, deviceKey = deviceKey) }
            .flatMap { accessRepository.save(it) }
    }

    private fun checkChallengeSolution(deviceKey: PublicKey, nonce: String): Boolean {
        val cipher = Cipher.getInstance(deviceKey.algorithm)
        cipher.init(Cipher.PUBLIC_KEY, deviceKey)

        try {
            val actualSolution = String(cipher.doFinal(nonce.toByteArray()))
            val expectedSolution = challenges.remove(deviceKey) ?: return false

            return actualSolution == expectedSolution
        } catch (e: Exception) {
            return false
        }
    }

}
