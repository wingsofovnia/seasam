package com.seasam.doorlocker.server.repository

import com.seasam.doorlocker.server.domain.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : ReactiveMongoRepository<User, String> {

}
