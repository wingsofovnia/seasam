package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Permission
import com.seasam.doorlocker.domain.ThingId
import com.seasam.doorlocker.domain.UserId
import java.time.LocalDateTime

data class PermissionDto(
    val thingId: ThingId,
    val grantedBy: UserId,
    val timestamp: LocalDateTime?) {

    companion object {
        fun from(p: Permission) = PermissionDto(p.thingId, p.grantedBy, p.timestamp)

        fun from(p: Set<Permission>) = p.map { from(it) }.toSet()
    }

    fun asPermission() = Permission(thingId, grantedBy)
}

fun Permission.asDto() = PermissionDto.from(this)
