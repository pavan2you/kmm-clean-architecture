@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref.concurrent.atomic

actual class AtomicInteger actual constructor(value: Int) {
    actual fun get(): Int {
        TODO("Not yet implemented")
    }

    actual fun set(value: Int) {
    }

    actual fun getAndSet(value: Int): Int {
        TODO("Not yet implemented")
    }

    actual fun compareAndSet(expect: Int, update: Int): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getAndIncrement(): Int {
        TODO("Not yet implemented")
    }
}