package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Permission
import com.seasam.doorlocker.domain.UserId
import java.time.LocalDateTime

data class PermissionDto(
    val userId: UserId,
    val timestamp: LocalDateTime?) {

    companion object {
        fun from(p: Permission) = PermissionDto(p.userId, p.timestamp)

        fun from(p: Set<Permission>) = p.map { from(it) }.toSet()
    }

    fun asPermission() = Permission(userId)
}

fun Permission.asDto() = PermissionDto.from(this)
