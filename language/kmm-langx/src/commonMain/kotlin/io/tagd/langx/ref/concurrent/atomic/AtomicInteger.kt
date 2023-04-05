package io.tagd.langx.ref.concurrent.atomic

expect class AtomicInteger(value: Int) {
    fun get(): Int
    fun set(value: Int)
    fun getAndSet(value: Int): Int
    fun compareAndSet(expect: Int, update: Int): Boolean
}