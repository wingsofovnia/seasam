package com.seasam.doorlocker.domain

import com.seasam.doorlocker.domain.credentials.UsernamePasswordCredentials
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.validation.constraints.NotBlank

@Document
class User(
    @Id val id: UUID = UUID.randomUUID(),
    @NotBlank var name: String,
    @NotBlank var email: String,
    var credentials: UsernamePasswordCredentials) {

    private val _devices: MutableSet<Device> = mutableSetOf()
    val devices: Set<Device> get() = _devices.toSet()
    fun addDevice(device: Device) = _devices.add(device)

    private val _permission: MutableSet<Permission> = mutableSetOf()
    val permission: Set<Permission> get() = _permission.toSet()
    fun addPermission(permission: Permission) = _permission.add(permission)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
