package io.tagd.core

interface Initializer<T> : Service {

    fun new(dependencies: Dependencies = dependencies()): T

    interface Builder<T, I : Initializer<T>> {

        fun build() : I
    }
}

interface ConditionalInitializer<T> : Initializer<T> {

    fun canInitialize(): Boolean

    fun conditionalNew(dependencies: Dependencies = dependencies()): T? {
        return if (canInitialize()) new(dependencies) else null
    }
}

class Dependencies : State()

fun dependencies(vararg pairs: Pair<String, Any?>): Dependencies {
    return Dependencies().apply {
        putAll(pairs)
    }
}
