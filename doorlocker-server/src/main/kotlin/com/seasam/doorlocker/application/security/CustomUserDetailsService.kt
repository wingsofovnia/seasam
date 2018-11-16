package com.seasam.doorlocker.application.security

import com.seasam.doorlocker.domain.UserRepository
import com.seasam.doorlocker.domain.UserStatus
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username).block()
            ?: throw UsernameNotFoundException("User with name $username not found.")
        return SeasamUserDetails(user)
    }


    private class SeasamUserDetails(private val user: com.seasam.doorlocker.domain.User) : UserDetails {

        override fun isEnabled() = user.status == UserStatus.ACTIVE

        override fun getUsername() = user.email

        override fun isCredentialsNonExpired() = isEnabled

        override fun getPassword() = user.password.toString()

        override fun isAccountNonExpired() = isEnabled

        override fun isAccountNonLocked() = user.status != UserStatus.BLOCKED

        override fun getAuthorities() = listOf(SimpleGrantedAuthority(user.role.name))
    }
}
