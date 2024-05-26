package io.tagd.arch.scopable

import io.tagd.arch.access.Injector
import io.tagd.arch.control.LoadingStateHandler
import io.tagd.langx.Callback

interface WithinScopableInjectionChain<S> : Injector {

    val within: S

    /**
     * Sets up the prerequisites. Typically useful to initialize things which are mandatory for the
     * rest of application functionality.
     *
     * Things like exception handler, scheduling strategy and any core platform related are some
     * of the examples.
     */
    fun setup() {}

    /**
     * The implementation must bind the dependencies lazily
     *
     * Note : The derived classes must call the super implementations at the end of the method,
     * this is very important to invoke the [callback] at right state of the application.
     *
     * Alternatively,
     *
     * the derived classes must do the all the work of super implementations and avoid calling
     * super method implementation.
     */
    fun initialize(callback: Callback<Unit>)

    /**
     *
     * This step is effective only if [within] wants to participate in loading phase. During this
     * step, the [WithinScopableInjectionChain] might load the desired dependencies to be part of the
     * loading phase of [WITHIN] - [Scopable].
     *
     * Here [WITHIN] may or may not be [S], it depends on who is triggering the
     * [registerLoadingSteps] call chain.
     *
     * Note : The derived classes must call the super implementations at the end of the method,
     * this is very important to invoke the [callback] at right state of the application.
     *
     * Alternatively,
     *
     * the derived classes must do the all the work of super implementations and avoid calling
     * super method implementation.
     */
    fun <WITHIN : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<WITHIN, *, *>,
        callback: Callback<Unit>
    )

    /**
     *
     * The implementation must load the dependencies which were bind lazily during [initialize]
     *
     * Note : The derived classes must call the super implementations at the end of the method,
     * this is very important to invoke the [callback] at right state of the application.
     *
     * Alternatively,
     *
     * the derived classes must do the all the work of super implementations and avoid calling
     * super method implementation.
     */
    override fun inject(callback: Callback<Unit>)
}