@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref.concurrent.atomic

actual class AtomicReference<V> actual constructor(value: V) {
    actual fun get(): V {
        TODO("Not yet implemented")
    }

    actual fun set(value: V) {
    }

    actual fun getAndSet(value: V): V {
        TODO("Not yet implemented")
    }

    actual fun compareAndSet(expect: V, update: V): Boolean {
        TODO("Not yet implemented")
    }
}