package io.tagd.arch.access

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.core.Service

interface Injector : Service, AsyncContext {

    fun inject()
}