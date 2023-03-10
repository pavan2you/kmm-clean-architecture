package io.tagd.arch.module

import io.tagd.core.Nameable
import io.tagd.core.Service
import io.tagd.di.Layer
import io.tagd.di.Scope
import io.tagd.di.layer

interface Module : Service, Nameable {

    abstract class Builder<T : Module> {

        abstract fun build(): T
    }
}

inline fun <reified T : Service> Module.inject(
    name: String,
    bindings: Layer<T>.() -> Unit
): Layer<T> {
    return Scope(name).locator.layer(bindings)
}