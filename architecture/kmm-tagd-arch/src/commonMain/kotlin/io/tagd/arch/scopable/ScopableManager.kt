package io.tagd.arch.scopable

import io.tagd.arch.control.LoadingStateHandler
import io.tagd.langx.Callback
import io.tagd.arch.rx.asyncForEach

abstract class ScopableManager : ScopableManagementSpec {

    override var outer: Scopable? = null

    @Suppress("MemberVisibilityCanBePrivate")
    protected val initializers = arrayListOf<ScopableInitializer<*>>()

    override fun initialize(outer: Scopable, callback: Callback<Unit>) {
        this.outer = outer

        loadScopableInitializers(initializers)
        initializers.asyncForEach(this, callback) { initializer, itemCallback ->
            initializer.initialize(outer, itemCallback)
        }
    }

    abstract fun loadScopableInitializers(initializers: ArrayList<ScopableInitializer<*>>)

    override fun <Outer : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<Outer, *, *>,
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

    abstract fun <Outer : Scopable> canRegisterLoadingSteps(
        handler: LoadingStateHandler<Outer, *, *>
    ): Boolean

    override fun inject(callback: Callback<Unit>) {
        asyncForEach(initializers, callback) { initializer, itemCallback ->
            initializer.inject(itemCallback)
        }
    }

    override fun release() {
        initializers.clear()
        outer = null
    }
}