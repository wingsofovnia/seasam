package com.seasam.doorlocker.domain.auth

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.security.*
import java.util.*

internal class ChallengeServiceTest {

    private lateinit var challengeService: ChallengeService

    private lateinit var testPublicKeyOne: PublicKey
    private lateinit var testPrivateKeyOne: PrivateKey
    private lateinit var testPublicKeyTwo: PublicKey
    private lateinit var testPrivateKeyTwo: PrivateKey

    @BeforeEach
    fun `Setup ChallengeService`() {
        challengeService = ChallengeService()
    }

    @BeforeEach
    fun `Prepare test keys`() {
        val generator = KeyPairGenerator.getInstance("RSA")

        val keyPairOne = generator.generateKeyPair()
        testPublicKeyOne = keyPairOne.public
        testPrivateKeyOne = keyPairOne.private

        val keyPairTwo = generator.generateKeyPair()
        testPublicKeyTwo = keyPairTwo.public
        testPrivateKeyTwo = keyPairTwo.private
    }

    @Test
    fun `#checkSolution ~ true for the keys from one pair and nonce assigned to it`() {
        val nonceForPublicKeyOne = challengeService.generateNonce(testPublicKeyOne)
        val nonceSignatureForPublicKeyOne = sign(nonceForPublicKeyOne, privateKey = testPrivateKeyOne)

        assertTrue(challengeService.verifyNonceSignature(testPublicKeyOne, nonceSignatureForPublicKeyOne))
    }

    @Test
    fun `#checkSolution ~ false for valid signature but nonce not assigned to the given device`() {
        val nonceForPublicKeyOne = challengeService.generateNonce(testPublicKeyOne)
        val nonceSignatureForPublicKeyTwo = sign(nonceForPublicKeyOne, privateKey = testPrivateKeyTwo)

        assertFalse(challengeService.verifyNonceSignature(testPublicKeyOne, nonceSignatureForPublicKeyTwo))
    }

    @Test
    fun `#checkSolution ~ SignatureException for invalid signature`() {
        val nonceForPublicKeyOne = challengeService.generateNonce(testPublicKeyOne)
        val invalidNonceSignatureForPublicKeyOne = UUID.randomUUID().toString().toByteArray()

        assertThrows<SignatureException> {
            challengeService.verifyNonceSignature(testPublicKeyOne, invalidNonceSignatureForPublicKeyOne)
        }
    }

    private fun sign(src: ByteArray, privateKey: PrivateKey) =
        Signature.getInstance(challengeService.signatureAlg).apply {
            initSign(privateKey)
            update(src)
        }.sign()
}
