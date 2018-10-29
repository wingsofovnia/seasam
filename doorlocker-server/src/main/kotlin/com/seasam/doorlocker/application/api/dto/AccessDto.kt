package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Access
import com.seasam.doorlocker.domain.AccessId
import com.seasam.doorlocker.domain.ThingId
import java.security.PublicKey
import java.time.LocalDateTime

data class AccessDto(
    var id: AccessId?,
    val thingId: ThingId,
    val permission: PermissionDto,
    val deviceKey: PublicKey,
    val timestamp: LocalDateTime) {

    companion object {
        fun from(a: Access) = AccessDto(a.id, a.thingId, PermissionDto.from(a.permission),
            a.deviceKey, a.timestamp)

        fun from(a: Collection<Access>) = a.map { from(it) }.toSet()
    }

    fun asAccess() = Access(id ?: AccessId(), thingId, permission.asPermission(), deviceKey)
}

fun Access.asDto() = AccessDto.from(this)
