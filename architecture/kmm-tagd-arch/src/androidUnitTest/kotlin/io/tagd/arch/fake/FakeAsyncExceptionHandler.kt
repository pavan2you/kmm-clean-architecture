package io.tagd.arch.fake

import io.tagd.arch.domain.crosscutting.async.AsyncExceptionHandler

class FakeAsyncExceptionHandler : AsyncExceptionHandler {

    override fun asyncException(throwable: Throwable) {
        throwable.printStackTrace()
    }
}