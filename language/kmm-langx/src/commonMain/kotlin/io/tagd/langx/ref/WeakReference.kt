package io.tagd.langx.ref

expect open class WeakReference<T>(value: T) {

    fun get(): T?

    fun clear()
}

expect fun <T> T.weak(): WeakReference<T>