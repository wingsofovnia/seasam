package com.seasam.doorlocker.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document
class Access(
    @Id val id: UUID = UUID.randomUUID(),
    val user: User,
    val thing: Thing,
    val device: Device,
    val permission: Permission,
    val timestamp: LocalDateTime = LocalDateTime.now()) {

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
