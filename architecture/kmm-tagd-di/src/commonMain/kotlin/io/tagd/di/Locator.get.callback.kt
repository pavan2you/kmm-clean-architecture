package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.core.async
import io.tagd.langx.Callback

fun <T : Service, S : T> Locator.get(clazz: Key<S>, callback: Callback<S?>) {
    async {
        val value = this.get<T, S>(clazz)
        callback.invoke(value)
    }
}

fun <T : Service, S : T> Locator.create(key: Key<S>, args: State? = null, callback: Callback<S?>) {
    async {
        val s = this.create<T, S>(key, args)
        callback.invoke(s)
    }
}

fun <T : Service, S : T> Locator.getLazy(key: Key<S>, args: State? = null, callback: Callback<S?>) {
    async {
        val s = this.getLazy<T, S>(key, args)
        callback.invoke(s)
    }
}