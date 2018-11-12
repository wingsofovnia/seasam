package com.seasam.doorlocker.domain.credentials.password

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PasswordTest {

    private lateinit var encoderMock: PasswordEncoder

    @BeforeEach
    fun setupEncoderMock() {
        encoderMock = mockk()

        every { encoderMock.encode(any()) } answers {
            it.invocation.args[0] as String
        }
        every { encoderMock.matches(any(), any()) } answers {
            it.invocation.args[0] == it.invocation.args[1]
        }
    }

    @Test
    fun `#create ~ PasswordEncoder#encode`() {
        val passwordRaw = "mypassword"
        val password = Password.create(passwordRaw, encoderMock)

        verify(exactly = 1) { encoderMock.encode(passwordRaw) }
        assertEquals(encoderMock.encode(passwordRaw), password.hash)
    }

    @Test
    fun `#matches ~ PasswordEncoder#maches`() {
        val passwordRaw = "mypassword"
        val password = Password.create(pwd = passwordRaw, encoder = encoderMock)

        assertTrue(password matches passwordRaw)
        verify(exactly = 1) { encoderMock.matches(passwordRaw, password.hash) }
    }

    @Test
    fun `String#toPassword ~ Password#create`() {
        val passwordRaw = "mypassword"

        assertEquals(Password.create(passwordRaw, encoderMock), passwordRaw.toPassword(encoderMock))
    }

    @Test
    fun `String#matches ~ Password#matches`() {
        val passwordRaw = "mypassword"
        val password = Password.create(passwordRaw, encoderMock)

        assertEquals(password.matches(passwordRaw), passwordRaw matches password)
    }
}
