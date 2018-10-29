package com.seasam.doorlocker.domain.auth

import com.seasam.doorlocker.domain.*
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.security.PublicKey

@Service
class AuthorizationService(private val thingRepository: ThingRepository,
                           private val userRepository: UserRepository,
                           private val accessRepository: AccessRepository) {

    fun authorize(deviceKey: PublicKey, thingId: ThingId): Mono<Access> =
        userRepository.findByDeviceKey(deviceKey).map { it.id }
            .flatMap { userId -> thingRepository.findById(thingId).map { it to userId } }
            .flatMapIterable { (thing, userId) -> thing.permissions.filter { it.userId == userId } }
            .next()
            .map { Access(thingId = thingId, permission = it, deviceKey = deviceKey) }
            .flatMap { accessRepository.save(it) }

}
