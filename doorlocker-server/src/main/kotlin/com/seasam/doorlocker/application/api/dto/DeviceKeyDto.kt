package com.seasam.doorlocker.application.api.dto

import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

data class DeviceKeyDto(
    val alg: String,
    val key: ByteArray
) {

    companion object {
        fun from(k: PublicKey) = DeviceKeyDto(k.algorithm, k.encoded)

        fun from(k: Collection<PublicKey>) = k.map { from(it) }.toSet()
    }

    fun asPublicKey(): PublicKey {
        val keySpec = X509EncodedKeySpec(key)
        val keyFactory = KeyFactory.getInstance(alg)
        return keyFactory.generatePublic(keySpec)
    }
}
