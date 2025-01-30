package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State

suspend fun <T : Service, S : T> Locator.getAsync(clazz: Key<S>): S? {
    return get<T, S>(clazz)
}

suspend fun <T : Service, S : T> Locator.createAsync(key: Key<S>, args: State? = null): S {
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

suspend fun <T : Service, S : T> Locator.getLazyAsync(key: Key<S>, args: State? = null): S {
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