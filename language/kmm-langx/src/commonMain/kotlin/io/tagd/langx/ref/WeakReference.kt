@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref

expect open class WeakReference<T>(value: T) {

    fun get(): T?

    fun clear()
}

expect fun <T> T.weak(): WeakReference<T>