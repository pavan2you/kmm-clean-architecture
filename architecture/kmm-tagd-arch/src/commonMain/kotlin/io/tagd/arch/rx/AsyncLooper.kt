package io.tagd.arch.rx

import io.tagd.arch.domain.usecase.Callback
import io.tagd.langx.ref.concurrent.atomic.AtomicInteger
import kotlin.jvm.JvmName

class AsyncLooper<T>(private val iterable: Iterable<T>, private val callback: Callback<Unit>) {

    fun forEach(item: (T, Callback<Unit>) -> Unit) {
        val size = iterable.count()
        if (size > 0) {
            val counter = AtomicInteger(0)

            iterable.forEach {
                item.invoke(it) {
                    val completed = counter.incrementAndGet()
                    if (completed == size) {
                        callback.invoke(Unit)
                    }
                }
            }
        } else {
            callback.invoke(Unit)
        }
    }
}

fun <T> asyncLooper(
    iterable: Iterable<T>,
    callback: Callback<Unit>
): AsyncLooper<T> {

    return AsyncLooper(iterable, callback)
}


fun <T> asyncForEach(
    iterable: Iterable<T>,
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {

    AsyncLooper(iterable, callback).forEach(item)
}

//todo revisit all async implementations
@JvmName("asyncForEachIterableItem")
fun <T> Iterable<T>.asyncForEach(
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {
    asyncForEach(this, callback, item)
}

fun <T> Iterable<T>.computeForEach(
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {
    asyncForEach(this, callback, item)
}

fun <T> Iterable<T>.ioForEach(
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {
    asyncForEach(this, callback, item)
}

fun <T> Iterable<T>.computeIOForEach(
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {
    asyncForEach(this, callback, item)
}

fun <T> Iterable<T>.daoForEach(
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {
    asyncForEach(this, callback, item)
}

fun <T> Iterable<T>.nwForEach(
    callback: Callback<Unit>,
    item: (T, Callback<Unit>) -> Unit
) {
    asyncForEach(this, callback, item)
}