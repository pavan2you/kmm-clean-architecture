package io.tagd.arch.rx

import io.tagd.arch.domain.crosscutting.async.CurrentStrategyResolver
import io.tagd.core.Service
import io.tagd.langx.collection.concurrent.ConcurrentLinkedQueue

interface AsyncTraceable : Service {

    val invoked: ConcurrentLinkedQueue<*>
}

object AsyncTrace {

    private val invoked: ConcurrentLinkedQueue<Any> = ConcurrentLinkedQueue()

    private val strategies
        get() = CurrentStrategyResolver.strategies()

    fun include(traceable: AsyncTraceable) {
        invoked.add(traceable)
    }

    fun exclude(traceable: AsyncTraceable) {
        invoked.remove(traceable)
    }
}