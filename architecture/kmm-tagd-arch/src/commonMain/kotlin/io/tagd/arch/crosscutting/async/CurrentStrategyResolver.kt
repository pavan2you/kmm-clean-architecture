package io.tagd.arch.crosscutting.async

import io.tagd.core.AsyncStrategy
import io.tagd.langx.Thread

object CurrentStrategyResolver {

    private val map = hashMapOf<String, AsyncStrategy>()

    fun include(strategy: AsyncStrategy): CurrentStrategyResolver {
        map[strategy.name] = strategy
        return this
    }

    fun resolve(): AsyncStrategy? {
        val threadName = Thread.currentThread().getName()
        val key = map.keys.firstOrNull {
            threadName.contains(it)
        }
        return map[key]
    }

    fun strategies(): MutableCollection<AsyncStrategy> {
        return map.values
    }
}