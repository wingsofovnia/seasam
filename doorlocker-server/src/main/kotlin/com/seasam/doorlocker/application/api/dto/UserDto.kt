package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.User
import com.seasam.doorlocker.domain.UserId
import com.seasam.doorlocker.domain.UserRole
import com.seasam.doorlocker.domain.UserStatus
import com.seasam.doorlocker.domain.credentials.password.Password
import javax.validation.constraints.NotBlank

data class UserDto(
    val id: UserId?,
    @NotBlank val name: String,
    @NotBlank val email: String,
    val password: String?,
    val devices: Set<DeviceDto>?,
    val permissions: Set<PermissionDto>?,
    val status: UserStatus?,
    val role: UserRole?) {

    companion object {
        fun from(u: User) = UserDto(u.id, u.name, u.email, null,
            DeviceDto.from(u.allDevices), PermissionDto.from(u.allPermissions), u.status, u.role)

        fun from(u: Collection<User>) = u.map { from(it) }
    }

    fun asUser() = User(id ?: UserId(), name, email, Password.create(password!!),
        devices?.map { it.asDevice() }?.toSet() ?: setOf(),
        permissions?.map { it.asPermission() }?.toSet() ?: setOf(),
        status ?: UserStatus.ACTIVE, role ?: UserRole.USER)
}
