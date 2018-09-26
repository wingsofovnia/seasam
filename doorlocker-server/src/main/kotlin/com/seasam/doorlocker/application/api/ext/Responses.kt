package com.seasam.doorlocker.application.api.ext

import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

fun <T> notFound() = ResponseEntity.notFound().build<T>()

fun <T> created(body: T?, at: URI) = ResponseEntity.created(at).body(body)

/**
 * Create a 201 ResponseEntity with URI pointing to address mapped
 * to specific controller's method provided via functional reference.
 *
 * Usage:
 * `created<ResBodyType>(body = [], at = Functional::reference, args = refsArgs)`
 *
 * E.g.:
 * `created<Any>(ThingResource::getOneThing, ThingId())`
 *
 * @param body ResponseEntity's body content
 * @param at Functional Reference to controller's method
 * @param args controller's method args
 *
 * @return [ResponseEntity] with 201 CREATED status, given body
 * and Location header set
 */
fun <T> created(body: T?, at: KFunction<*>, vararg args: Any?): ResponseEntity<T> {
    val uri = ControllerLinkBuilder.linkTo(at.javaMethod, *args).toUri()
    return ResponseEntity.created(uri).body<T>(body)
}

fun <T> ok(body: T) = ResponseEntity.ok(body)

fun noContent() = ResponseEntity.status(HttpStatus.NO_CONTENT).build<Void>()
