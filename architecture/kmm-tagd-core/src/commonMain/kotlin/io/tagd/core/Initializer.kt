package io.tagd.core

interface Initializer<T> : Service {

    fun new(dependencies: Dependencies): T

    interface Builder<T, I : Initializer<T>> {

        fun build() : I
    }

    override fun release() {
        //no-op
    }
}

interface ConditionalInitializer<T> : Initializer<T> {

    fun canInitialize(): Boolean

    fun conditionalNew(dependencies: Dependencies): T? {
        return if (canInitialize()) new(dependencies) else null
    }
}

class Dependencies : State() {

    override operator fun plus(pairs: Array<out Pair<String, Any?>>): Dependencies {
        super.plus(pairs)
        return this
    }

    override operator fun plus(map: Map<String, Any?>): Dependencies {
        super.plus(map)
        return this
    }

    override operator fun plus(other: State): Dependencies {
        super.plus(other)
        return this
    }
}

fun dependencies(vararg pairs: Pair<String, Any?>): Dependencies {
    return Dependencies().apply {
        putAll(pairs)
    }
}

fun State.asDependencies() : Dependencies {
    return Dependencies().also {
        it.putAll(this)
    }
}
