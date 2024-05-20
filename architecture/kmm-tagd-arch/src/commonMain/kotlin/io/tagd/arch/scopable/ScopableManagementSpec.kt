package io.tagd.arch.scopable

import io.tagd.arch.control.LoadingStateHandler
import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.langx.Callback
import io.tagd.core.Service

interface ScopableManagementSpec : Service, AsyncContext {

    val outer: Scopable?

    fun initialize(outer: Scopable, callback: Callback<Unit>)

    fun <Outer : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<Outer, *, *>,
        callback: Callback<Unit>
    )

    fun inject(callback: Callback<Unit>)
}