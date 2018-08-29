package com.seasam.doorlocker.domain.credentials.password

import org.mindrot.jbcrypt.BCrypt

private const val DEFAULT_BCRYPT_ROUNDS = 10

class BCryptPasswordEncoder(val rounds: Int = DEFAULT_BCRYPT_ROUNDS) : PasswordEncoder {

    override fun encode(password: CharSequence): String {
        return BCrypt.hashpw(password.toString(), BCrypt.gensalt(rounds))
    }

    override fun matches(password: CharSequence, hash: String): Boolean {
        return BCrypt.checkpw(password.toString(), hash)
    }
}
