package com.seasam.doorlocker.domain

import java.time.LocalDateTime

data class Access(
    val userId: UserId,
    val device: Device,
    val permission: Permission) {

    val timestamp: LocalDateTime = LocalDateTime.now()
}
