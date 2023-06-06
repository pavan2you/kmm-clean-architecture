package io.tagd.core

fun interface ValueProvider<T> {

    fun value(): T
}