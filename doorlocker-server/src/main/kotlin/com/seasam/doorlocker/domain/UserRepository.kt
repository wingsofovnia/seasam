package com.seasam.doorlocker.domain

import com.seasam.doorlocker.domain.credentials.password.Password
import org.reactivestreams.Publisher
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.PublicKey

@Repository
interface UserRepository : ReactiveCrudRepository<User, UserId> {

    @Query("{ 'email' : ?#{#email}, 'password.hash' : ?#{#password.hash}  }")
    fun findByEmailAndPassword(email: String, password: Password): Mono<User>

    @Query("{ '_devices.key' : ?0 }")
    fun findByDeviceKey(publicKey: PublicKey): Mono<User>

    fun findAllByStatus(status: UserStatus): Flux<User>

    fun findByEmail(email: String): Mono<User>

    @JvmDefault
    fun findAllActive(): Flux<User> = findAllByStatus(UserStatus.ACTIVE)

    fun countByStatus(status: UserStatus): Mono<Long>

    @JvmDefault
    fun countActive(): Mono<Long> = countByStatus(UserStatus.ACTIVE)

    fun findAllByIdAndStatus(ids: MutableIterable<UserId>, status: UserStatus): Flux<User>

    @JvmDefault
    fun findAllActiveById(ids: MutableIterable<UserId>): Flux<User> = findAllByIdAndStatus(ids, UserStatus.ACTIVE)

    fun findAllByIdAndStatus(idStream: Publisher<UserId>, status: UserStatus): Flux<User>

    @JvmDefault
    fun findAllActiveById(idStream: Publisher<UserId>): Flux<User> = findAllByIdAndStatus(idStream, UserStatus.ACTIVE)

    fun existsByIdAndStatus(id: UserId, status: UserStatus): Mono<Boolean>

    @JvmDefault
    fun existsActiveById(id: UserId): Mono<Boolean> = existsByIdAndStatus(id, UserStatus.ACTIVE)

    fun existsByIdAndStatus(id: Publisher<UserId>, status: UserStatus): Mono<Boolean>

    @JvmDefault
    fun existsActiveById(id: Publisher<UserId>): Mono<Boolean> = existsByIdAndStatus(id, UserStatus.ACTIVE)

    fun findByIdAndStatus(id: UserId, status: UserStatus): Mono<User>

    @JvmDefault
    fun findActiveById(id: UserId): Mono<User> = findByIdAndStatus(id, UserStatus.ACTIVE)

    fun findByIdAndStatus(id: Publisher<UserId>, status: UserStatus): Mono<User>

    @JvmDefault
    fun findActiveById(id: Publisher<UserId>): Mono<User> = findByIdAndStatus(id, UserStatus.ACTIVE)

    @JvmDefault
    fun deactivateUser(userId: UserId): Mono<Void> {
        return findById(userId)
            .doOnNext { u -> u.status = UserStatus.INACTIVE }
            .flatMap { u -> save(u) }
            .then()
    }

    @JvmDefault
    fun activateUser(userId: UserId): Mono<Void> {
        return findById(userId)
            .doOnNext { u -> u.status = UserStatus.ACTIVE }
            .flatMap { u -> save(u) }
            .then()
    }
}
