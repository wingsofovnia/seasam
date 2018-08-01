package com.seasam.doorlocker.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DoorLockerApplication

fun main(args: Array<String>) {
    runApplication<DoorLockerApplication>(*args)
}
