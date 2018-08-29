package com.seasam.doorlocker.domain.credentials.password

class Password constructor(var hash: String, private val encoder: PasswordEncoder) {

    companion object {
        fun create(from: String, encoder: PasswordEncoder = BCryptPasswordEncoder()): Password {
            return Password(hash = encoder.encode(from), encoder = encoder)
        }
    }

    infix fun matches(password: CharSequence): Boolean {
        return encoder.matches(password, hash = this.hash)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Password

        if (hash != other.hash) return false
        if (encoder.javaClass != other.encoder.javaClass) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hash.hashCode()
        result = 31 * result + encoder.hashCode()
        return result
    }


}

fun String.toPassword(encoder: PasswordEncoder = BCryptPasswordEncoder()): Password {
    return Password.create(from = this, encoder = encoder)
}

infix fun String.matches(password: Password): Boolean {
    return password.matches(password = this)
}
