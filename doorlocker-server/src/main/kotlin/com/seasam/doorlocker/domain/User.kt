package com.seasam.doorlocker.domain

import com.seasam.doorlocker.domain.credentials.password.Password
import org.springframework.data.mongodb.core.mapping.Document
import java.security.PublicKey
import javax.validation.constraints.NotBlank

@Document
class User(
    val id: UserId = UserId(),
    @NotBlank var name: String,
    @NotBlank var email: String,
    var password: Password,
    devices: Set<Device> = setOf(),
    permissions: Set<Permission> = setOf(),
    var status: UserStatus = UserStatus.ACTIVE,
    var role: UserRole = UserRole.USER) {

    private val devices: MutableSet<Device> = mutableSetOf()
    val allDevices: Set<Device> get() = devices.toSet()

    private val permissions: MutableSet<Permission> = mutableSetOf()
    val allPermissions: Set<Permission> get() = permissions.toSet()

    init {
        this.devices.addAll(devices)
        this.permissions.addAll(permissions)
    }

    fun addDevice(device: Device) = devices.add(device)

    fun findDevice(key: PublicKey) = devices.find { it.key == key }

    fun hasDevice(key: PublicKey) = findDevice(key) != null

    fun hasDevice(device: Device) = findDevice(device.key) != null

    fun removeDevice(key: PublicKey) = devices.removeIf { it.key == key }

    fun removeDevice(device: Device) = devices.remove(device)

    fun addPermission(permission: Permission) = permissions.add(permission)

    fun findPermission(thingId: ThingId) = permissions.find { it.thingId == thingId }

    fun hasPermission(thingId: ThingId) = findPermission(thingId) != null

    fun removePermission(thingId: ThingId) = permissions.removeIf { it.thingId == thingId }

    fun removePermission(permission: Permission) = permissions.remove(permission)

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
