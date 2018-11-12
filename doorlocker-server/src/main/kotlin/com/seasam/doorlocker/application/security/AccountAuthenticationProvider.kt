package com.seasam.doorlocker.application.security

import com.seasam.doorlocker.domain.credentials.password.Password
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountAuthenticationProvider(private val userDetailsService: CustomUserDetailsService, private val passwordEncoder: PasswordEncoder) : AbstractUserDetailsAuthenticationProvider() {

    @Throws(AuthenticationException::class)
    override fun additionalAuthenticationChecks(userDetails: UserDetails, token: UsernamePasswordAuthenticationToken) {
        if (token.credentials == null || userDetails.password == null) {
            throw BadCredentialsException("Credentials may not be null.")
        }
        if (!Password.from(userDetails.password).matches(token.credentials.toString())) {
            throw BadCredentialsException("Invalid credentials.")
        }
    }

    @Throws(AuthenticationException::class)
    override fun retrieveUser(username: String, token: UsernamePasswordAuthenticationToken): UserDetails {
        return userDetailsService.loadUserByUsername(username)
    }

}
