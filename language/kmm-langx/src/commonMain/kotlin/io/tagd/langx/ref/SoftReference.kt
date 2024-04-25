@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref

expect class SoftReference<T>(value: T) {

    fun get(): T?

    fun clear()
}

expect fun <T> T.soft(): SoftReference<T>