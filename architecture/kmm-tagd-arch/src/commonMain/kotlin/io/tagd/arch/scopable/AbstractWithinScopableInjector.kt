package io.tagd.arch.scopable

import io.tagd.arch.control.LoadingStateHandler
import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.arch.rx.asyncForEach
import io.tagd.langx.Callback
import io.tagd.langx.ref.WeakReference
import io.tagd.langx.ref.weak

abstract class AbstractWithinScopableInjector<S : Scopable>(within: S) :
    WithinScopableInjectionChain<S> {

    private var weakWithin: WeakReference<S?>? = within.weak()

    override val within: S
        get() = weakWithin?.get()!!


    @Suppress("MemberVisibilityCanBePrivate")
    protected val initializers = arrayListOf<WithinScopableInitializer<S, *>>()

    override fun initialize(callback: Callback<Unit>) {
        load(initializers)
        asyncForEach(initializers, callback) { initializer, itemCallback ->
            initializer.initialize(itemCallback)
        }
    }

    abstract fun load(initializers: ArrayList<WithinScopableInitializer<S, *>>)

    override fun <WITHIN : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<WITHIN, *, *>,
        callback: Callback<Unit>
    ) {

        if (canRegisterLoadingSteps(handler)) {
            initializers.asyncForEach(this, callback) { initializer, itemCallback ->
                initializer.registerLoadingSteps(handler, itemCallback)
            }
        } else {
            callback.invoke(Unit)
        }
    }

    abstract fun <WITHIN : Scopable> canRegisterLoadingSteps(
        handler: LoadingStateHandler<WITHIN, *, *>
    ): Boolean

    override fun inject(callback: Callback<Unit>) {
        asyncForEach(initializers, callback) { initializer, itemCallback ->
            initializer.inject(itemCallback)
        }
    }

    override fun release() {
        cancelAsync()
        initializers.forEach {
            it.release()
        }
        initializers.clear()
        weakWithin?.clear()
        weakWithin = null
    }
}