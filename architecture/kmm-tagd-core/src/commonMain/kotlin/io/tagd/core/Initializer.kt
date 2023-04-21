package io.tagd.core

interface Initializer<T> : Releasable {

    fun new(vararg args: Any? = arrayOf()): T

    interface Builder<T, I : Initializer<T>> {

        fun build() : I
    }
}
