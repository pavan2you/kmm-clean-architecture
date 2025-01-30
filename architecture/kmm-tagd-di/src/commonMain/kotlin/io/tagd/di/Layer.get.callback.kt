package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.core.async
import io.tagd.langx.Callback

fun <S : T, T : Service> Layer<T>.get(service: Key<*>, callback: Callback<S?>) {
    async {
        val value = get<S>(service)
        callback.invoke(value)
    }
}

fun <S : T, T : Service> Layer<T>.create(
    service: Key<S>,
    args: State? = null,
    callback: Callback<S?>
) {

    async {
        val value = create(service, args)
        callback.invoke(value)
    }
}

fun <S : T, T : Service> Layer<T>.getLazy(
    service: Key<S>,
    args: State? = null,
    callback: Callback<S?>
) {

    async {
        val value = getLazy(service, args)
        callback.invoke(value)
    }
}