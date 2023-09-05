package io.tagd.langx.ref

actual typealias SoftReference<T> = java.lang.ref.SoftReference<T>

actual fun <T> T.soft(): SoftReference<T> {
    return SoftReference(this)
}