package com.seasam.doorlocker.domain

import java.util.*

class UserId : Id {

    constructor() : super()
    constructor(uuidStr: String) : super(uuidStr)
    constructor(uuid: UUID) : super(uuid)
}
