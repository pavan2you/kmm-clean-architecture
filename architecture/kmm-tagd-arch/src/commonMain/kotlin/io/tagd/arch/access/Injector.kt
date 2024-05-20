package io.tagd.arch.access

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.langx.Callback
import io.tagd.core.Service

interface Injector : Service, AsyncContext {

    fun setup()

    fun inject(callback: Callback<Unit>)

    fun injectSynchronously()

    fun injectAsynchronously(callback: Callback<Unit>)
}