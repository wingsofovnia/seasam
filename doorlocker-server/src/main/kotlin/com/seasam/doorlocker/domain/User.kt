package com.seasam.doorlocker.domain

import com.seasam.doorlocker.domain.credentials.password.Password
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.security.PublicKey
import javax.validation.constraints.NotBlank

@Document
class User(
    val id: UserId = UserId(),
    @NotBlank var name: String,
    @NotBlank var email: String,
    var password: Password,
    @Value("#root._devices") devices: Set<Device> = setOf(),
    var status: UserStatus = UserStatus.ACTIVE,
    var role: UserRole = UserRole.USER) {

    private val _devices: MutableSet<Device> = mutableSetOf()

    val devices: Set<Device> get() = _devices.toSet()

    init {
        this._devices.addAll(devices)
    }

    fun addDevice(device: Device) = _devices.add(device)

    fun findDevice(key: PublicKey) = _devices.find { it.key == key }

    fun hasDevice(key: PublicKey) = findDevice(key) != null

    fun hasDevice(device: Device) = findDevice(device.key) != null

    fun removeDevice(key: PublicKey) = _devices.removeIf { it.key == key }

    fun removeDevice(device: Device) = _devices.remove(device)

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
