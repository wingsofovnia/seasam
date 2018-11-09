package com.seasam.doorlocker

import com.seasam.doorlocker.domain.User
import com.seasam.doorlocker.domain.UserRepository
import com.seasam.doorlocker.domain.credentials.password.Password
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.beans.factory.annotation.Autowired



@SpringBootApplication
class DoorLockerApplication(@Autowired
                            private val repository: UserRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        repository.deleteAll().block()
        val email = "email1"
        val password = Password.create("ololo")
        val expectedUser = User(password = password, email = email, name = "Name")
        repository.save(expectedUser).block()
    }

}
fun main(args: Array<String>) {
    runApplication<DoorLockerApplication>(*args)
}


