package com.seasam.doorlocker.domain

import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document
class Thing(
    val id: ThingId = ThingId(),
    @NotBlank var name: String,
    @Value("#root._permissions") permissions: Set<Permission> = setOf()) {

    private val _permissions: MutableSet<Permission> = mutableSetOf()
    val permissions: Set<Permission> get() = _permissions.toSet()

    init {
        this._permissions.addAll(permissions)
    }

    fun addPermission(permission: Permission) = _permissions.add(permission)

    fun findPermission(userId: UserId) = _permissions.find { it.userId == userId }

    fun hasPermission(userId: UserId) = findPermission(userId) != null

    fun removePermission(userId: UserId) = _permissions.removeIf { it.userId == userId }

    fun removePermission(permission: Permission) = _permissions.remove(permission)

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
