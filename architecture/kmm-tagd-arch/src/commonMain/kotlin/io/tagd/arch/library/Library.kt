package io.tagd.arch.library

import io.tagd.core.Nameable
import io.tagd.core.Service
import io.tagd.di.Layer
import io.tagd.di.Scope
import io.tagd.di.layer

interface Library : Service, Nameable {

    abstract class Builder<T : Library> {

        abstract fun build(): T
    }
}

inline fun <reified T : Service> Library.inject(
    name: String,
    bindings: Layer<T>.() -> Unit
): Layer<T> {
    return Scope(name).locator.layer(bindings)
}