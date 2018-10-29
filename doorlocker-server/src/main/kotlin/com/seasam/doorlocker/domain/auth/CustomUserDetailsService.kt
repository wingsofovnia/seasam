package com.seasam.doorlocker.domain.auth

import com.seasam.doorlocker.application.api.dto.asDto
import com.seasam.doorlocker.domain.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails {
        val user = userRepository.findByEmail(email!!)
            .map { it.asDto() }
            .block()
            ?: throw UsernameNotFoundException("User $email not found.")

        // TODO add roles check

        return User(user.email, user.password, true, true, true, true, Collections.emptyList())

    }

}
