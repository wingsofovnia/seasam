package com.seasam.doorlocker.application.security.config

import com.seasam.doorlocker.application.security.AccountAuthenticationProvider
import com.seasam.doorlocker.application.security.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfiguration(private val userDetailsService: CustomUserDetailsService, private val accountAuthenticationProvider: AccountAuthenticationProvider) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth!!.userDetailsService<UserDetailsService>(userDetailsService)
        auth.authenticationProvider(accountAuthenticationProvider)
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setSigningKey(SIGNING_KEY)
        return jwtAccessTokenConverter
    }

    companion object {

        private val SIGNING_KEY = "s1f41234pwqdqkl4l12ghg9853123sd"
    }

}
