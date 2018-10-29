package com.seasam.doorlocker.application.config

import com.seasam.doorlocker.domain.auth.CustomUserDetailsService
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore


@Configuration
class OAuth2ServerConfiguration {

    @Configuration
    @EnableResourceServer
    protected class ResourceServerConfiguration(private val jwtAccessTokenConverter: JwtAccessTokenConverter) : ResourceServerConfigurerAdapter() {

        override fun configure(resources: ResourceServerSecurityConfigurer?) {
            resources!!
                .tokenStore(JwtTokenStore(jwtAccessTokenConverter))
        }

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected class AuthorizationServerConfiguration(private val jwtAccessTokenConverter: JwtAccessTokenConverter,
                                                     private val passwordEncoder: BCryptPasswordEncoder,
                                                     private val authenticationManager: AuthenticationManager, private val customUserDetailsService: CustomUserDetailsService) : AuthorizationServerConfigurerAdapter() {

        @Throws(Exception::class)
        override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
            endpoints!!
                .tokenStore(JwtTokenStore(jwtAccessTokenConverter))
                .authenticationManager(authenticationManager)
                .userDetailsService(customUserDetailsService)
                .accessTokenConverter(jwtAccessTokenConverter)
        }

//        @Throws(Exception::class)
//        override fun configure(clients: ClientDetailsServiceConfigurer?) {
//            clients!!
//                .inMemory()
//                .withClient("client")
//                .secret(passwordEncoder.encode("secret"))
//                .authorizedGrantTypes("password", "refresh_token")
//                .scopes("read", "write")
//        }

    }
}
