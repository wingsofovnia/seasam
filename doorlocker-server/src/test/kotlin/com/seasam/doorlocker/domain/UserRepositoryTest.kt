package com.seasam.doorlocker.domain

import com.seasam.doorlocker.domain.credentials.password.Password
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
@DataMongoTest
internal class UserRepositoryTest {

    @Autowired
    lateinit var repo: UserRepository

    @Test
    fun `#findByUsernameAndPassword ~ User with given username & password`() {
        val email = "email"
        val password = Password.create("ololo")
        val expectedUser = User(password = password, email = email, name = "Name")
        repo.save(expectedUser).block()

        val actualPassenger = repo.findByEmailAndPassword(email, password).block()
        Assertions.assertEquals(expectedUser, actualPassenger)
    }

    @Test
    fun `#deactivateUser ~ Deactivated user with given id `() {
        val user = User(password = Password.create("ololo"), email = "email", name = "Name")

        val savedUser = repo.save(user).block()!!
        Assertions.assertEquals(UserStatus.ACTIVE, savedUser.status)

        repo.deactivateUser(savedUser.id).block()

        val deactivatedUser = repo.findById(savedUser.id).block()!!
        Assertions.assertEquals(UserStatus.INACTIVE, deactivatedUser.status)
    }

    @Test
    fun `#activateUser ~ Activated user with given id `() {
        val user = User(password = Password.create("ololo"), email = "email", name = "Name",
            status = UserStatus.INACTIVE)

        val savedUser = repo.save(user).block()!!
        Assertions.assertEquals(UserStatus.INACTIVE, savedUser.status)

        repo.activateUser(savedUser.id).block()

        val activatedUser = repo.findById(savedUser.id).block()!!
        Assertions.assertEquals(UserStatus.ACTIVE, activatedUser.status)
    }
}
