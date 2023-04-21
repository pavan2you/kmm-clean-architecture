package io.tagd.core

interface Initializer<T> : Service {

    fun new(vararg args: Any? = arrayOf()): T

    interface Builder<T, I : Initializer<T>> {

        fun build() : I
    }
}

interface ConditionalInitializer<T> : Initializer<T> {

    fun canInitialize(): Boolean

    fun conditionalNew(vararg args: Any? = arrayOf()): T? {
        return if (canInitialize()) new(args) else null
    }
}
