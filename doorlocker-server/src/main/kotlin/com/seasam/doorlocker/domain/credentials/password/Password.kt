package com.seasam.doorlocker.domain.credentials.password

class Password constructor(var hash: String, private val encoder: PasswordEncoder) {

    companion object {
        /**
         * Creates a Password object from a **plain text** password encoding it with given PasswordEncoder
         */
        fun create(pwd: String, encoder: PasswordEncoder = BCryptPasswordEncoder()): Password {
            return from(hash = encoder.encode(pwd), encoder = encoder)
        }

        /**
         * Wraps an **encoded** password into a Password object
         */
        fun from(hash: String, encoder: PasswordEncoder = BCryptPasswordEncoder()): Password {
            return Password(hash = hash, encoder = encoder)
        }
    }

    /**
     * Checks whether the given plain text password is encodable into this Password,
     * i.e. whether this Password object is an encoded version of given plain text password
     */
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

    override fun toString(): String {
        return hash
    }
}

fun String.toPassword(encoder: PasswordEncoder = BCryptPasswordEncoder()): Password {
    return Password.create(pwd = this, encoder = encoder)
}

infix fun String.matches(password: Password): Boolean {
    return password.matches(password = this)
}

/**
 * Checks if this string is an encoded password (hash) kept in the given Password object
 */
infix fun String.represents(password: Password): Boolean {
    return password.hash == this
}
