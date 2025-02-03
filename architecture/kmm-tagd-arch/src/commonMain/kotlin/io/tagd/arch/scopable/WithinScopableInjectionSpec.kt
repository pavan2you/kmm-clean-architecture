package io.tagd.arch.scopable

import io.tagd.arch.access.Injector
import io.tagd.arch.app.LoadingStateHandler
import io.tagd.langx.Callback
import io.tagd.core.Initializer

/**
 * An injection spec which happens under given [within] [Scopable].
 *  - The [within] acts as the sandbox / outer scope in which this injection chain takes place.
 *  - use [setup] as the bootstrapping step.
 *  - use [initialize] for the lazy creations to take place, after that signal [Callback] the
 *  caller.
 *  - use [registerLoadingSteps] if there are any heavy computational steps must be included as
 *  part of the ancestor/outer scopable's loading step.
 *  - use [inject] to place the injection bindings. By now, the lazy composition results are
 *  available which are set as part of [initialize]. By using them, injection bindings can setup.
 *
 *  Typically this [WithinScopableInjectionSpec] is applied for [Initializer] or [Injector].
 */
interface WithinScopableInjectionSpec<S : Scopable> : Injector {

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
     * Note 1 : The derived classes must call the super implementations at the end of the method,
     * this is very important to invoke the [callback] at right state of the application.
     *
     * Alternatively,
     *
     * the derived classes must do the all the work of super implementations and avoid calling
     * super method implementation.
     *
     * Note 2 : Observe the result type is [Unit] in [Callback]. It is just to signal that the
     * [initialize] lazy composition is done.
     */
    fun initialize(callback: Callback<Unit>)

    /**
     * This step is effective only if [within] wants to participate in loading phase. During this
     * step, the [WithinScopableInjectionSpec] might load the desired dependencies to be part of
     * the loading phase of [WITHIN] - [Scopable].
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