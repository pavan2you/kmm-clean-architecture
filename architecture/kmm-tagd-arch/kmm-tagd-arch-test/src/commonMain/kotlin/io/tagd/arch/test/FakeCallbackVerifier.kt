package io.tagd.arch.test

import io.tagd.arch.domain.usecase.Callback
import io.tagd.core.assert

/**
 * This class is used to mock a callback
 * It can be used to verify
 * 1. the method call - [verifyMethodCall]
 * 2. verifying the result passed in [Callback] - [verifyResult]
 *
 * Use [reset] to reset the values after each use
 * or, use [verifyAll] to verify and reset
 */
class FakeCallbackVerifier {

    private var isCalled = false
    private var result: Any? = null

    val mockMethod: Callback<Any> = {
        isCalled = true
        result = it
    }

    fun verifyAll(expected: Any?) {
        verifyResult(expected)
        verifyMethodCall()
        reset()
    }

    fun verifyAll(assert: Callback<Any?>) {
        verifyResult(assert)
        verifyMethodCall()
        reset()
    }

    fun reset() {
        isCalled = false
        result = null
    }

    private fun verifyMethodCall() {
        assert(isCalled)
    }

    private fun verifyResult(assert: Callback<Any?>) {
        assert.invoke(result)
    }

    private fun verifyResult(expected: Any?) {
        assert(expected == result)
    }

}