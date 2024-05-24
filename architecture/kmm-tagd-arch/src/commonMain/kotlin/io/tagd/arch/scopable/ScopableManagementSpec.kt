package io.tagd.arch.scopable

import io.tagd.arch.control.LoadingStateHandler
import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.langx.Callback
import io.tagd.core.Service

interface ScopableManagementSpec : Service, AsyncContext {

    val outer: Scopable?

    /**
     * Note : The derived classes must call the super implementations at the end of the method,
     * this is very important to invoke the [callback] at right state of the application.
     *
     * Alternatively,
     *
     * the derived classes must do the all the work of super implementations and avoid calling
     * super method implementation.
     */
    fun initialize(outer: Scopable, callback: Callback<Unit>)

    /**
     * Note : The derived classes must call the super implementations at the end of the method,
     * this is very important to invoke the [callback] at right state of the application.
     *
     * Alternatively,
     *
     * the derived classes must do the all the work of super implementations and avoid calling
     * super method implementation.
     */
    fun <Outer : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<Outer, *, *>,
        callback: Callback<Unit>
    )

    /**
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