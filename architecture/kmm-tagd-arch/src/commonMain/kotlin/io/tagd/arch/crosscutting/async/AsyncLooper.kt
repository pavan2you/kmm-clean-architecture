@file:Suppress("unused")

package io.tagd.arch.crosscutting.async

import io.tagd.arch.access.crosscutting
import io.tagd.arch.crosscutting.async.ComputationStrategy
import io.tagd.arch.crosscutting.async.ComputeIOStrategy
import io.tagd.arch.crosscutting.async.DaoIOStrategy
import io.tagd.arch.crosscutting.async.DiskIOStrategy
import io.tagd.arch.crosscutting.async.NetworkIOStrategy
import io.tagd.core.AsyncContext
import io.tagd.core.AsyncStrategy
import io.tagd.core.ExecutionContext
import io.tagd.langx.Callback
import io.tagd.langx.collection.concurrent.ConcurrentLinkedQueue
import io.tagd.langx.ref.WeakReference
import io.tagd.langx.ref.concurrent.atomic.AtomicInteger
import io.tagd.langx.ref.weak
import kotlin.jvm.JvmName

open class AsyncLooper<T>(
    callerContext: AsyncContext,
    private val executor: AsyncStrategy,
    private val iterable: Iterable<T>,
    private val callback: Callback<Unit>
) : AsyncTraceable {

    private var weakContext : WeakReference<AsyncContext>? = callerContext.weak()

    private val context
        get() = weakContext?.get()

    override val invoked: ConcurrentLinkedQueue<T> = ConcurrentLinkedQueue()

    init {
        @Suppress("LeakingThis")
        (AsyncTrace.include(this))
    }

    open fun forEach(item: (T, Callback<Unit>) -> Unit) {
        val callerContext = context
        if (callerContext == null) {
            finish()
        } else {
            executor.execute(callerContext) {
                forEachAsync(it, item)
            }
        }
    }

    private fun forEachAsync(context: ExecutionContext, itemBlock: (T, Callback<Unit>) -> Unit) {
        val size = iterable.count()
        if (size > 0) {
            val counter = AtomicInteger(0)

            iterable.forEach { item ->
                invoked.add(item)
                itemBlock.invoke(item) {
                    invoked.remove(item)
                    val completed = counter.incrementAndGet()
                    if (completed == size) {
                        context.notify {
                            finish()
                        }
                    }
                }
            }
        } else {
            context.notify {
                finish()
            }
        }
    }

    private fun finish() {
        AsyncTrace.exclude(this)
        callback.invoke(Unit)
        release()
    }

    override fun release() {
        weakContext?.clear()
        weakContext = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AsyncLooper<*>

        return callback === other.callback
    }

    override fun hashCode(): Int {
        return callback.hashCode()
    }
}

fun <T> AsyncContext.asyncLooper(
    iterable: Iterable<T>,
    callback: Callback<Unit>
): AsyncLooper<T> {

    return AsyncLooper(this, crosscutting<ComputationStrategy>()!!, iterable, callback)
}

fun <T> AsyncContext.asyncLooper(
    iterable: Iterable<T>,
    executor: AsyncStrategy,
    callback: Callback<Unit>
): AsyncLooper<T> {

    return AsyncLooper(this, executor, iterable, callback)
}

fun <T> AsyncContext.asyncForEach(
    iterable: Iterable<T>,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    asyncForEach(crosscutting<ComputationStrategy>()!!, iterable, callback, item)
}

fun <T> AsyncContext.asyncForEach(
    executor: AsyncStrategy,
    iterable: Iterable<T>,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    AsyncLooper(this, executor, iterable, callback).forEach(item)
}

@JvmName("asyncForEachIterableItem")
fun <T> Iterable<T>.asyncForEach(
    context: AsyncContext,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    context.asyncForEach(this, callback, item)
}

fun <T> Iterable<T>.computeForEach(
    context: AsyncContext,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    context.asyncForEach(this, callback, item)
}

fun <T> Iterable<T>.ioForEach(
    context: AsyncContext,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    context.asyncForEach(crosscutting<NetworkIOStrategy>()!!, this, callback, item)
}

fun <T> Iterable<T>.computeIOForEach(
    context: AsyncContext,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    context.asyncForEach(crosscutting<ComputeIOStrategy>()!!, this, callback, item)
}

fun <T> Iterable<T>.diskIOForEach(
    context: AsyncContext,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    context.asyncForEach(crosscutting<DiskIOStrategy>()!!, this, callback, item)
}

fun <T> Iterable<T>.daoForEach(
    context: AsyncContext,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    context.asyncForEach(crosscutting<DaoIOStrategy>()!!, this, callback, item)
}

fun <T> Iterable<T>.nwForEach(
    context: AsyncContext,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {
    context.asyncForEach(crosscutting<NetworkIOStrategy>()!!, this, callback, item)
}