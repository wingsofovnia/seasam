package com.seasam.doorlocker.domain

import java.security.PublicKey

data class Device(
    val name: String,
    val key: PublicKey)
