package com.seasam.doorlocker.domain.auth

import org.springframework.stereotype.Service
import java.security.PublicKey
import java.util.*
import javax.crypto.Cipher

@Service
class ChallengeService {

    private val nonces = HashMap<PublicKey, ByteArray>()

    fun generateChallenge(deviceKey: PublicKey): ByteArray {
        val nonce = randomBytes(CHALLENGE_SIZE_BYTES)
        nonces[deviceKey] = nonce
        return nonce
    }

    fun checkSolution(deviceKey: PublicKey, solution: ByteArray): Boolean {
        val cipher = Cipher.getInstance(deviceKey.algorithm)
        cipher.init(Cipher.PUBLIC_KEY, deviceKey)

        val actualNonce = cipher.doFinal(solution)
        val expectedNonce = nonces.remove(deviceKey) ?: return false

        return expectedNonce contentEquals actualNonce
    }

    internal fun forgetNonces() = nonces.clear()

    private fun randomBytes(size: Int) = ByteArray(size).apply { Random().nextBytes(this) }


    companion object {
        const val CHALLENGE_SIZE_BYTES = 128
    }
}
