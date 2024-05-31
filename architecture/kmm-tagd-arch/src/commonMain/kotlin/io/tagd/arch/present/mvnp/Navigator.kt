package io.tagd.arch.present.mvnp

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.core.Service

interface Navigator<out N : Navigatable> : Service, AsyncContext {

    val navigatable: N?
}