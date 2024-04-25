@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref

actual typealias WeakReference<T> = java.lang.ref.WeakReference<T>

actual fun <T> T.weak(): WeakReference<T> {
    return WeakReference(this)
}