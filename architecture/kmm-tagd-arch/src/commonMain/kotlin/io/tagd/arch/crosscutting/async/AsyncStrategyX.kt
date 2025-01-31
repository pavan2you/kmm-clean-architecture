package io.tagd.arch.crosscutting.async

import io.tagd.arch.access.crosscutting
import io.tagd.core.AsyncContext
import io.tagd.core.AsyncStrategy
import io.tagd.core.ExecutionContext

interface PresentationStrategy : AsyncStrategy
interface ComputationStrategy : AsyncStrategy
interface ComputeIOStrategy : AsyncStrategy
interface NetworkIOStrategy : AsyncStrategy
interface DiskIOStrategy : AsyncStrategy
interface DaoIOStrategy : AsyncStrategy
interface CacheIOStrategy : AsyncStrategy

fun AsyncContext.compute(
    context: AsyncContext? = this,
    delay: Long = 0,
    computation: (ExecutionContext) -> Unit
) {

    val strategy = crosscutting<ComputationStrategy>()
    strategy?.execute(context, delay, computation)
}

fun AsyncContext.present(
    context: AsyncContext? = this,
    delay: Long = 0,
    presentation: (ExecutionContext) -> Unit
) {

    val strategy = crosscutting<PresentationStrategy>()
    strategy?.execute(context, delay, presentation)
}

fun AsyncContext.networkIO(
    context: AsyncContext? = this,
    delay: Long = 0,
    api: (ExecutionContext) -> Unit
) {

    val strategy = crosscutting<NetworkIOStrategy>()
    strategy?.execute(context, delay, api)
}

fun AsyncContext.computeIO(
    context: AsyncContext? = this,
    delay: Long = 0,
    api: (ExecutionContext) -> Unit
) {

    val strategy = crosscutting<ComputeIOStrategy>()
    strategy?.execute(context, delay, api)
}

fun AsyncContext.diskIO(
    context: AsyncContext? = this,
    delay: Long = 0,
    operation: (ExecutionContext) -> Unit
) {

    val strategy = crosscutting<DiskIOStrategy>()
    strategy?.execute(context, delay, operation)
}

fun AsyncContext.daoIO(
    context: AsyncContext? = this,
    delay: Long = 0,
    crudOperation: (ExecutionContext) -> Unit
) {

    val strategy = crosscutting<DaoIOStrategy>()
    strategy?.execute(context, delay, crudOperation)
}

fun AsyncContext.cacheIO(
    context: AsyncContext? = this,
    delay: Long = 0,
    operation: (ExecutionContext) -> Unit
) {

    val strategy = crosscutting<CacheIOStrategy>()
    strategy?.execute(context, delay, operation)
}

fun AsyncContext.cancelAsync(context: AsyncContext = this) {
    cancelPresentations(context)
    cancelComputations(context)
    cancelComputeIO(context)
    cancelNetworkIO(context)
    cancelDiskIO(context)
    cancelDaoIO(context)
    cancelCacheIO(context)
}

fun AsyncContext.cancelPresentations(context: AsyncContext = this) {
    val strategy = crosscutting<PresentationStrategy>()
    strategy?.cancel(context)
}

fun AsyncContext.cancelComputations(context: AsyncContext = this) {
    val strategy = crosscutting<ComputationStrategy>()
    strategy?.cancel(context)
}

fun AsyncContext.cancelComputeIO(context: AsyncContext = this) {
    val strategy = crosscutting<ComputeIOStrategy>()
    strategy?.cancel(context)
}

fun AsyncContext.cancelNetworkIO(context: AsyncContext = this) {
    val strategy = crosscutting<NetworkIOStrategy>()
    strategy?.cancel(context)
}

fun AsyncContext.cancelDiskIO(context: AsyncContext = this) {
    val strategy = crosscutting<DiskIOStrategy>()
    strategy?.cancel(context)
}

fun AsyncContext.cancelDaoIO(context: AsyncContext = this) {
    val strategy = crosscutting<DaoIOStrategy>()
    strategy?.cancel(context)
}

fun AsyncContext.cancelCacheIO(context: AsyncContext = this) {
    val strategy = crosscutting<CacheIOStrategy>()
    strategy?.cancel(context)
}