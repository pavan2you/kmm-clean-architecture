package io.tagd.langx.ref

expect class WeakReference<T>(value: T) {

    fun get(): T?

    fun clear()
}

expect fun <T> T.weak(): WeakReference<T>