package com.seasam.doorlocker.application.api.ext

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

internal class ResponsesTest {

    @RestController
    class TestController {

        @GetMapping("/tests/{testId}")
        fun getTest(@PathVariable testId: UUID) {}

        @GetMapping("/tests/{testId}/{superTestId}")
        fun getTestMultiple(@PathVariable testId: UUID, @PathVariable superTestId: UUID) {}
    }

    @Test
    fun `#create(Any, Any (FunctionReference), Any?) ~ ControllerLinkBuilder#linkTo`() {
        val testId = UUID.randomUUID()

        val responseEntity = created(null, TestController::getTest, testId)
        val actualUri = responseEntity.headers["Location"]!!.first()

        val getTestMethod = TestController::class.java.getMethod("getTest", UUID::class.java)
        val expectedUri = linkTo(getTestMethod, testId).toString()

        Assertions.assertEquals(expectedUri, actualUri)
    }

    @Test
    fun `#create(Any, Any (FunctionReference), vararg Any?) ~ ControllerLinkBuilder#linkTo`() {
        val testId = UUID.randomUUID()
        val superTestId = UUID.randomUUID()

        val responseEntity = created(null, TestController::getTestMultiple, testId, superTestId)
        val actualUri = responseEntity.headers["Location"]!!.first()

        val getTestMethod = TestController::class.java
            .getMethod("getTestMultiple", UUID::class.java, UUID::class.java)
        val expectedUri = linkTo(getTestMethod, testId, superTestId).toString()

        Assertions.assertEquals(expectedUri, actualUri)
    }
}
