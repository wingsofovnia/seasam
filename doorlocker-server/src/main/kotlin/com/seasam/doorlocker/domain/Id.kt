package com.seasam.doorlocker.domain

import com.fasterxml.jackson.annotation.JsonValue
import java.io.Serializable
import java.util.*

open class Id : Serializable {

    private val value: UUID

    constructor() {
        this.value = UUID.randomUUID()
    }

    constructor(uuidStr: String) {
        this.value = UUID.fromString(uuidStr)
    }

    constructor(uuid: UUID) {
        this.value = uuid
    }

    @JsonValue
    override fun toString(): String {
        return value.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Id

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}
