package com.seasam.doorlocker.domain.credentials.password

interface PasswordEncoder {

    fun encode(password: CharSequence): String

    fun matches(password: CharSequence, hash: String): Boolean
}
