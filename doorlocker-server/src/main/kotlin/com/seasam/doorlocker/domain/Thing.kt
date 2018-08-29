package com.seasam.doorlocker.domain

import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document
class Thing(
    val id: ThingId = ThingId(),
    @NotBlank var name: String,
    accesses: Set<Access> = setOf()) {

    private val _accesses: MutableSet<Access> = mutableSetOf()
    val accesses: Set<Access> = _accesses.toSet()
    fun recordAccess(access: Access) = _accesses.add(access)

    init {
        this._accesses.addAll(accesses)
    }

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
