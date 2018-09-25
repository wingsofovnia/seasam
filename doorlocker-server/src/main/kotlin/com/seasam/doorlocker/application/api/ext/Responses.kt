package com.seasam.doorlocker.application.api.ext

import org.springframework.hateoas.mvc.ControllerLinkBuilder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI
import kotlin.reflect.KClass

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
fun <T> created(body: T?, at: Any, vararg args: Any?): ResponseEntity<T> {
    if (at !is kotlin.jvm.internal.FunctionReference)
        throw IllegalArgumentException("FunctionReference is expected")

    val fuRefMethodNameGetter = at.javaClass.getDeclaredMethod("getName").apply { isAccessible = true }
    val referenceMethodName = fuRefMethodNameGetter.invoke(at) as String

    val fuRefMethodOwnerClassGetter = at.javaClass.getDeclaredMethod("getOwner").apply { isAccessible = true }
    val referenceClass = fuRefMethodOwnerClassGetter.invoke(at) as KClass<*>

    val argsTypes = args.mapNotNull { it?.javaClass }.toTypedArray()
    val method = referenceClass.java.getDeclaredMethod(referenceMethodName, *argsTypes)
    val uri = ControllerLinkBuilder.linkTo(method, *args).toUri()

    return ResponseEntity.created(uri).body<T>(body)
}

fun <T> ok(body: T) = ResponseEntity.ok(body)

fun noContent() = ResponseEntity.status(HttpStatus.NO_CONTENT).build<Void>()
