package com.seasam.doorlocker.domain

import java.util.*

class AccessId : Id {

    constructor() : super()
    constructor(uuidStr: String) : super(uuidStr)
    constructor(uuid: UUID) : super(uuid)
}
