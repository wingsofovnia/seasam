package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Thing
import com.seasam.doorlocker.domain.ThingId
import javax.validation.constraints.NotBlank

data class ThingDto(
    var id: ThingId?,
    @NotBlank var name: String) {

    companion object {
        fun from(t: Thing) = ThingDto(t.id, t.name)

        fun from(t: Collection<Thing>) = t.map { from(it) }
    }

    fun asThing() = Thing(id ?: ThingId(), name)
}
