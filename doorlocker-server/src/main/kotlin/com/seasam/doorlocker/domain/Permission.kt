package com.seasam.doorlocker.domain

import java.time.LocalDateTime

data class Permission(val userId: UserId) {

    val timestamp: LocalDateTime = LocalDateTime.now()
}
