package io.tagd.arch.access

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.core.Service

interface Injector : Service, AsyncContext {

    fun setup()

    fun inject()

    @Deprecated("no need to expose")
    fun injectSynchronously()

    @Deprecated("no need to expose")
    fun injectAsynchronously()
}