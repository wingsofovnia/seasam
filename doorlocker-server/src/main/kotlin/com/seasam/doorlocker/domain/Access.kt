package com.seasam.doorlocker.domain

import java.time.LocalDateTime

data class Access(
    val user: UserId,
    val device: Device,
    val timestamp: LocalDateTime = LocalDateTime.now())
