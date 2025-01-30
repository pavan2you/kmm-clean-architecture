package io.tagd.arch.test

import io.tagd.core.AsyncExceptionHandler

class FakeAsyncExceptionHandler : AsyncExceptionHandler {

    override fun asyncException(throwable: Throwable) {
        throwable.printStackTrace()
    }
}