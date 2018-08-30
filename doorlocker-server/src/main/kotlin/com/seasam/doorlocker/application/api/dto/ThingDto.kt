package com.seasam.doorlocker.application.api.dto

import com.seasam.doorlocker.domain.Thing
import com.seasam.doorlocker.domain.ThingId
import javax.validation.constraints.NotBlank

data class ThingDto(
    val id: ThingId = ThingId(),
    @NotBlank var name: String,
    val accesses: Set<AccessDto>?) {

    companion object {
        fun from(t: Thing) = ThingDto(t.id, t.name, AccessDto.from(t.allAccesses))

        fun from(t: Collection<Thing>) = t.map { from(it) }
    }

    fun asThing() = Thing(id, name, accesses?.map { it.asAccess() }?.toSet() ?: setOf())
}
