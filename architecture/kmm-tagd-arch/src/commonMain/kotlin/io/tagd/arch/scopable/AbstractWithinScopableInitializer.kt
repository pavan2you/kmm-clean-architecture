package io.tagd.arch.scopable

import io.tagd.arch.app.LoadingStateHandler
import io.tagd.core.Dependencies
import io.tagd.core.dependencies
import io.tagd.langx.Callback
import io.tagd.langx.ref.WeakReference
import io.tagd.langx.ref.weak

/**
 * An abstract implementation of [WithinScopableInitializer]. If the produced [T] is having
 * an associated [AbstractWithinScopableInjector], then the derived classes must delegate
 * (but not necessary) [initialize], [registerLoadingSteps] and [inject] to
 * [AbstractWithinScopableInjector].
 */
abstract class AbstractWithinScopableInitializer<S : Scopable, T>(within: S) :
    WithinScopableInitializer<S, T> {

    private var weakWithin: WeakReference<S?>? = within.weak()

    override val within: S
        get() = weakWithin?.get()!!

    val outerScope
        get() = within.thisScope

    override fun initialize(callback: Callback<Unit>) {

        //no-op just trigger callback
        callback.invoke(Unit)
    }

    override fun <WITHIN : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<WITHIN, *, *>,
        callback: Callback<Unit>
    ) {

        //no-op just trigger callback
        callback.invoke(Unit)
    }

    override fun inject(callback: Callback<Unit>) {

        //no-op just trigger callback
        callback.invoke(Unit)
    }

    protected open fun newDependencies(): Dependencies {
        return dependencies(
            ARG_OUTER_SCOPE to outerScope
        )
    }

    override fun release() {
        weakWithin?.clear()
        weakWithin = null
        super.release()
    }

    companion object {
        const val ARG_OUTER_SCOPE = WithinScopableInitializer.ARG_OUTER_SCOPE
    }
}