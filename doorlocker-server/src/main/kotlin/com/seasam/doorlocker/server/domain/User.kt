package com.seasam.doorlocker.server.domain

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
    val id: String,
    val surname: String,
    val name: String,
    val email: String? = null,
    val publicKey: String? = null,
    val doorsAccess: List<DoorAccess>? = null

)
