package io.tagd.arch.access

import io.tagd.core.AsyncContext
import io.tagd.core.Service
import io.tagd.langx.Callback

interface Injector : Service, AsyncContext {

    /**
     * Initializes the dependencies and notifies the caller using [callback]
     *
     * Note : The derived classes must call the super implementations at the end of the method,
     * this is very important to invoke the [callback] at right state of the application.
     *
     * Alternatively,
     *
     * the derived classes must do the all the work of super implementations and avoid calling
     * super method implementation.
     */
    fun inject(callback: Callback<Unit>)
}