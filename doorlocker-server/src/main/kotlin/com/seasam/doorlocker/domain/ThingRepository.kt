package com.seasam.doorlocker.domain

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ThingRepository : ReactiveCrudRepository<Thing, ThingId>
