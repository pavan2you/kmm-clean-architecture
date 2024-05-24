package io.tagd.arch.scopable

import io.tagd.arch.access.AsyncInitializer
import io.tagd.arch.control.LoadingStateHandler
import io.tagd.core.Dependencies
import io.tagd.langx.Callback

interface ScopableInitializer<S : Scopable?> : AsyncInitializer<S>, ScopableManagementSpec {

    interface Builder<S : Scopable, I : ScopableInitializer<S>> : AsyncInitializer.Builder<S, I>
}

abstract class AbstractScopableInitializer<S : Scopable?> : ScopableInitializer<S?> {

    override var outer: Scopable? = null

    override fun initialize(outer: Scopable, callback: Callback<Unit>) {
        this.outer = outer
        callback.invoke(Unit)
    }

    override fun <Outer : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<Outer, *, *>,
        callback: Callback<Unit>
    ) {

        //no-op just trigger callback
        callback.invoke(Unit)
    }

    override fun inject(callback: Callback<Unit>) {
        //no-op just trigger callback
        callback.invoke(Unit)
    }

    override fun release() {
        outer = null
        super.release()
    }
}