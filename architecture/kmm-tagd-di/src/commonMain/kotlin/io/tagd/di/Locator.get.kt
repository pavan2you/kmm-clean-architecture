package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State

fun <T : Service, S : T> Locator.get(clazz: Key<S>): S? {
    return layers()
        ?.values
        ?.firstOrNull { it?.contains(clazz) ?: false }
        ?.let {
            it.get(clazz) as S?
        }
}

fun <T : Service, S : T> Locator.create(key: Key<S>, args: State? = null): S {
    var exception: Exception? = null
    var s: S? = null

    layers()
        ?.values
        ?.firstOrNull { it?.contains(key) ?: false }
        ?.let {
            @Suppress("UNCHECKED_CAST")
            try {
                s = (it as Layer<T>).create(key, args)
            } catch (e: Exception) {
                exception = e
            }
        }

    return s ?: throw exception!!
}

fun <T : Service, S : T> Locator.getLazy(key: Key<S>, args: State? = null): S {
    var exception: Exception? = null
    var s: S? = null

    layers()
        ?.values
        ?.firstOrNull { it?.contains(key) ?: false }
        ?.let {
            @Suppress("UNCHECKED_CAST")
            try {
                s = (it as Layer<T>).getLazy(key, args)
            } catch (e: Exception) {
                exception = e
            }
        }

    return s ?: throw exception!!
}