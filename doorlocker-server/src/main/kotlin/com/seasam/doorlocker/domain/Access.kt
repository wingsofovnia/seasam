package com.seasam.doorlocker.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.security.PublicKey
import java.time.LocalDateTime

@Document
class Access(
    val id: AccessId = AccessId(),

    val thingId: ThingId,
    val permission: Permission,
    val deviceKey: PublicKey) {

    val timestamp: LocalDateTime = LocalDateTime.now()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Access

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
