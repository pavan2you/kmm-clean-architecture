package io.tagd.core

interface ValueProvider<T> {

    fun value(): T
}