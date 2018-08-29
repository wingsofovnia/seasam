package com.seasam.doorlocker.domain

import java.time.LocalDateTime

data class Permission(
    val thingId: ThingId,
    val grantedBy: UserId,
    val timestamp: LocalDateTime = LocalDateTime.now())
