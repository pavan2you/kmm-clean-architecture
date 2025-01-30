package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State

suspend fun <S : T, T : Service> Layer<T>.getAsync(service: Key<*>): S? {
    return get(service)
}

suspend fun <S : T, T : Service> Layer<T>.createAsync(
    service: Key<S>,
    args: State? = null
): S {

    return create(service, args)
}

suspend fun <S : T, T : Service> Layer<T>.getLazyAsync(
    service: Key<S>,
    args: State? = null
): S {

    return getLazy(service, args)
}