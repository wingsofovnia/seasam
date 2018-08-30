package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Device

data class DeviceDto(
    val name: String,
    val key: DeviceKeyDto
) {
    companion object {
        fun from(d: Device) = DeviceDto(d.name, DeviceKeyDto.from(d.key))

        fun from(d: Collection<Device>) = d.map { from(it) }.toSet()
    }

    fun asDevice() = Device(name, key.asPublicKey())
}
