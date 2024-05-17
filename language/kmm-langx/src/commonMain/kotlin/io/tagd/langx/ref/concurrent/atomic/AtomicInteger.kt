@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.ref.concurrent.atomic

expect class AtomicInteger(value: Int) {

    fun get(): Int

    fun getAndIncrement(): Int

    fun incrementAndGet(): Int

    fun set(value: Int)

    fun getAndSet(value: Int): Int

    fun compareAndSet(expect: Int, update: Int): Boolean
}