package io.tagd.arch.access

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.core.Service
import io.tagd.langx.Callback

interface Injector : Service, AsyncContext {

    fun inject(callback: Callback<Unit>)
}