package com.seasam.doorlocker.domain

import com.seasam.doorlocker.domain.credentials.password.Password
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : ReactiveCrudRepository<User, UserId> {

    @Query("{ 'email' : ?#{#email}, 'password.hash' : ?#{#password.hash}  }")
    fun findByEmailAndPassword(email: String, password: Password): Mono<User>
}
