package com.seasam.doorlocker.domain

import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document
class Thing(
    val id: ThingId = ThingId(),
    @NotBlank var name: String,
    accesses: Set<Access> = setOf()) {

    private val accesses: MutableSet<Access> = mutableSetOf()
    val allAccesses: Set<Access> = accesses.toSet()

    init {
        this.accesses.addAll(accesses)
    }

    fun recordAccess(access: Access) = accesses.add(access)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Thing

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
