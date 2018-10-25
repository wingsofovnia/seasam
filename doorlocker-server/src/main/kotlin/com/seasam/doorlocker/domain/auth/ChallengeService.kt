package com.seasam.doorlocker.domain.auth

import org.springframework.stereotype.Service
import java.security.PublicKey
import java.security.Signature
import java.util.*

@Service
class ChallengeService(val nonceSize: Int = 128,
                       val signatureAlg: String = "SHA1withRSA") {

    private val nonces = HashMap<PublicKey, ByteArray>()

    fun generateNonce(deviceKey: PublicKey): ByteArray {
        val nonce = randomBytes(nonceSize)
        nonces[deviceKey] = nonce
        return nonce
    }

    fun verifyNonceSignature(deviceKey: PublicKey, signature: ByteArray): Boolean {
        val expectedNonce = nonces.remove(deviceKey) ?: return false

        val sig = Signature.getInstance(signatureAlg).apply {
            initVerify(deviceKey)
            update(expectedNonce)
        }

        return sig.verify(signature)
    }

    private fun randomBytes(size: Int) = ByteArray(size).apply { Random().nextBytes(this) }
}
