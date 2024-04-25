@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref

actual typealias SoftReference<T> = java.lang.ref.SoftReference<T>

actual fun <T> T.soft(): SoftReference<T> {
    return SoftReference(this)
}