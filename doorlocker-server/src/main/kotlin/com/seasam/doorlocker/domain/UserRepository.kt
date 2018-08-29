package com.seasam.doorlocker.domain

import com.seasam.doorlocker.domain.credentials.password.Password
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

@Repository
interface UserRepository : ReactiveCrudRepository<User, UUID> {

    @Query("{ 'credentials.username' : ?#{#username}, 'credentials.password.hash' : ?#{#password.hash}  }")
    fun findByUsernameAndPassword(username: String, password: Password): Mono<User>
}
