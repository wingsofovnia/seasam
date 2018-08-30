package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Access
import com.seasam.doorlocker.domain.UserId
import java.time.LocalDateTime

data class AccessDto(
    val user: UserId,
    val device: DeviceDto,
    val permission: PermissionDto,
    val timestamp: LocalDateTime) {

    companion object {
        fun from(a: Access) = AccessDto(a.user, DeviceDto.from(a.device), PermissionDto.from(a.permission), a.timestamp)

        fun from(a: Collection<Access>) = a.map { from(it) }.toSet()
    }

    fun asAccess() = Access(user, device.asDevice(), permission.asPermission())
}
