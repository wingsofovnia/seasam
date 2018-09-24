package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Access
import com.seasam.doorlocker.domain.AccessId
import com.seasam.doorlocker.domain.UserId
import java.time.LocalDateTime

data class AccessDto(
    var id: AccessId?,
    val userId: UserId,
    val device: DeviceDto,
    val permission: PermissionDto,
    val timestamp: LocalDateTime) {

    companion object {
        fun from(a: Access) = AccessDto(a.id, a.userId, DeviceDto.from(a.device),
            PermissionDto.from(a.permission), a.timestamp)

        fun from(a: Collection<Access>) = a.map { from(it) }.toSet()
    }

    fun asAccess() = Access(id ?: AccessId(), userId, device.asDevice(), permission.asPermission())
}
