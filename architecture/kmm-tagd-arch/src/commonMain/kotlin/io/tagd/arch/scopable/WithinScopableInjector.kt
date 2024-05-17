package io.tagd.arch.scopable

import io.tagd.arch.access.Injector

interface WithinScopableInjector<S : Scopable> : Injector {

    val scopable: Scopable?
}