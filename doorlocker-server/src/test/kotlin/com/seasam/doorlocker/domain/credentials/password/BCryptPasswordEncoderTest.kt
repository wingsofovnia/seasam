package com.seasam.doorlocker.domain.credentials.password

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BCryptPasswordEncoderTest {

    private lateinit var bCryptEncoder: BCryptPasswordEncoder

    @BeforeEach
    fun setupBCryptEncoder() {
        bCryptEncoder = BCryptPasswordEncoder()
    }

    @Test
    fun `#encode ~ hash not equals to raw password`() {
        val passwordRaw = "mypassword"
        val password = Password.create(passwordRaw, bCryptEncoder)

        assertNotEquals(passwordRaw, password.hash)
    }

    @Test
    fun `#matches ~ true with origin password`() {
        val passwordRaw = "mypassword"
        val password = Password.create(passwordRaw, bCryptEncoder)

        assertTrue(password matches passwordRaw)
    }

    @Test
    fun `#matches ~ false with a substring of origin password`() {
        val passwordRaw = "mypassword"
        val password = Password.create(passwordRaw, bCryptEncoder)

        val passwordRawSubstring = "mypassword".substring(3)

        assertFalse(password matches passwordRawSubstring)
    }
}
