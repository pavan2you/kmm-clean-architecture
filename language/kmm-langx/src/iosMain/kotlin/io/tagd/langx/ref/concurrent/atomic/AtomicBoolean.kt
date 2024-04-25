@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref.concurrent.atomic

actual class AtomicBoolean actual constructor(value: Boolean) {
    actual fun get(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun set(value: Boolean) {
    }

    actual fun getAndSet(value: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    actual fun compareAndSet(expect: Boolean, update: Boolean): Boolean {
        TODO("Not yet implemented")
    }
}