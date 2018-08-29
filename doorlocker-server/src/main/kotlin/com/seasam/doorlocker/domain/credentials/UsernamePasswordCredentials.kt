package com.seasam.doorlocker.domain.credentials

import com.seasam.doorlocker.domain.credentials.password.Password

class UsernamePasswordCredentials : Credentials {

    val username: String
    val password: Password

    constructor(username: String, password: Password) {
        this.username = if (!username.isEmpty()) username else throw IllegalArgumentException("Empty username")
        this.password = password
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UsernamePasswordCredentials

        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}
