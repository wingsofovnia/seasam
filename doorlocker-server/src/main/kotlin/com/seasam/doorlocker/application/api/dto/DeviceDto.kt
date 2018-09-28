package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Device
import java.security.PublicKey

data class DeviceDto(
    val name: String,
    val key: PublicKey
) {
    companion object {
        fun from(d: Device) = DeviceDto(d.name, d.key)

        fun from(d: Collection<Device>) = d.map { from(it) }.toSet()
    }

    fun asDevice() = Device(name, key)
}

fun Device.asDto() = DeviceDto.from(this)
