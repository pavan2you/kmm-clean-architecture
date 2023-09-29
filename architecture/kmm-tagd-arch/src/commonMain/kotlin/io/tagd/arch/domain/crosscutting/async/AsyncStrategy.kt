/*
 * Copyright (C) 2021 The TagD Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.tagd.arch.domain.crosscutting.async

import io.tagd.arch.access.crosscutting
import io.tagd.arch.domain.crosscutting.CrossCutting
import io.tagd.core.Cancellable
import io.tagd.core.Releasable

interface AsyncExceptionHandler {

    fun asyncException(throwable: Throwable)
}

/**
 * Any class which is performing async operations, must be an AsyncContext.
 */
interface AsyncContext : Releasable

interface AsyncStrategy : CrossCutting, Cancellable {

    val exceptionHandler: AsyncExceptionHandler?

    fun execute(context: AsyncContext? = null, delay: Long = 0, work: () -> Unit)
}

interface PresentationStrategy : AsyncStrategy

interface ComputationStrategy : AsyncStrategy

interface ComputeIOStrategy : AsyncStrategy

interface NetworkIOStrategy : AsyncStrategy

interface DiskIOStrategy : AsyncStrategy

interface DaoIOStrategy : AsyncStrategy

interface CacheIOStrategy : AsyncStrategy

fun AsyncContext.compute(context: AsyncContext? = this, delay: Long = 0, computation: () -> Unit) {
    val strategy = crosscutting<ComputationStrategy>()
    strategy?.execute(context, delay, computation)
}

fun AsyncContext.present(context: AsyncContext? = this, delay: Long = 0, presentation: () -> Unit) {
    val strategy = crosscutting<PresentationStrategy>()
    strategy?.execute(context, delay, presentation)
}

fun AsyncContext.networkIO(context: AsyncContext? = this, delay: Long = 0, api: () -> Unit) {
    val strategy = crosscutting<NetworkIOStrategy>()
    strategy?.execute(context, delay, api)
}

fun AsyncContext.computeIO(context: AsyncContext? = this, delay: Long = 0, api: () -> Unit) {
    val strategy = crosscutting<ComputeIOStrategy>()
    strategy?.execute(context, delay, api)
}

fun AsyncContext.diskIO(context: AsyncContext? = this, delay: Long = 0, operation: () -> Unit) {
    val strategy = crosscutting<DiskIOStrategy>()
    strategy?.execute(context, delay, operation)
}

fun AsyncContext.daoIO(
    context: AsyncContext? = this,
    delay: Long = 0,
    crudOperation: () -> Unit
) {
    val strategy = crosscutting<DaoIOStrategy>()
    strategy?.execute(context, delay, crudOperation)
}

fun AsyncContext.cacheIO(context: AsyncContext? = this, delay: Long = 0, operation: () -> Unit) {
    val strategy = crosscutting<CacheIOStrategy>()
    strategy?.execute(context, delay, operation)
}

typealias ObserveOn = (AsyncContext, Long, () -> Unit) -> Unit

typealias ExecuteOn = (AsyncContext, Long, () -> Unit) -> Unit

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