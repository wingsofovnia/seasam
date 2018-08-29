package com.seasam.doorlocker.domain.credentials

interface Credentials {

    override infix fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}
