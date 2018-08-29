package com.seasam.doorlocker.domain

import com.seasam.doorlocker.domain.credentials.UsernamePasswordCredentials
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank

@Document
class User(
    val id: UserId = UserId(),
    @NotBlank var name: String,
    @NotBlank var email: String,
    var credentials: UsernamePasswordCredentials,
    devices: Set<Device> = setOf(),
    permissions: Set<Permission> = setOf()) {

    private val devices: MutableSet<Device> = mutableSetOf()
    val allDevices: Set<Device> get() = devices.toSet()

    private val permissions: MutableSet<Permission> = mutableSetOf()
    val allPermissions: Set<Permission> get() = permissions.toSet()

    init {
        this.devices.addAll(devices)
        this.permissions.addAll(permissions)
    }

    fun addDevice(device: Device) = devices.add(device)

    fun addPermission(permission: Permission) = permissions.add(permission)

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
