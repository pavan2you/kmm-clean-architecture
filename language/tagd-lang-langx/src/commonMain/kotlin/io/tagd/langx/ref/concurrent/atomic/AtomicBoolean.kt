package io.tagd.langx.ref.concurrent.atomic

expect class AtomicBoolean(value: Boolean) {
    fun get(): Boolean
    fun set(value: Boolean)
    fun getAndSet(value: Boolean): Boolean
    fun compareAndSet(expect: Boolean, update: Boolean): Boolean
}