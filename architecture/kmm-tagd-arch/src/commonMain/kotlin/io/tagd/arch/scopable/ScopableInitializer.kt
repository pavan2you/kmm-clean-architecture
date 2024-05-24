package io.tagd.arch.scopable

import io.tagd.arch.access.AsyncInitializer

interface ScopableInitializer<S : Scopable> : AsyncInitializer<S>, ScopableManagementSpec {

    interface Builder<S : Scopable, I : ScopableInitializer<S>> : AsyncInitializer.Builder<S, I>
}