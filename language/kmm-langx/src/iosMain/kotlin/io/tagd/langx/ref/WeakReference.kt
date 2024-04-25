@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref

actual open class WeakReference<T> actual constructor(value: T) {
    actual fun get(): T? {
        TODO("Not yet implemented")
    }

    actual fun clear() {
    }

}

actual fun <T> T.weak(): WeakReference<T> {
    return WeakReference(this)
}