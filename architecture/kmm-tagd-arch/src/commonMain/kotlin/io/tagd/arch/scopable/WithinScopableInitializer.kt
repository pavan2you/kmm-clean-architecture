package io.tagd.arch.scopable

import io.tagd.arch.access.AsyncInitializer
import io.tagd.langx.Callback

interface WithinScopableInitializer<S : Scopable, T> : AsyncInitializer<T>,
    WithinScopableInjectionChain<S> {

    /**
     * The typical implementation would be
     *  1. prepare the [io.tagd.core.Dependencies],
     *  2. call [new]
     */
    override fun initialize(callback: Callback<Unit>)

    companion object {
        const val ARG_OUTER_SCOPE = "outer_scope"
    }

    interface Builder<S : Scopable, T, I : WithinScopableInitializer<S, T>> :
        AsyncInitializer.Builder<T, I>
}

