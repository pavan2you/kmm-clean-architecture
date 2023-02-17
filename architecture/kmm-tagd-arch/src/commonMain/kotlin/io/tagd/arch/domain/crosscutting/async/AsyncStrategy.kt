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

interface AsyncStrategy : CrossCutting, Cancellable {

    fun execute(context: Any? = null, delay: Long = 0, work: () -> Unit)
}

interface PresentationStrategy : AsyncStrategy

interface ComputationStrategy : AsyncStrategy

interface NetworkIOStrategy : AsyncStrategy

interface DiskIOStrategy : AsyncStrategy

interface DaoStrategy : AsyncStrategy

fun Any.compute(context: Any? = this, delay: Long = 0, computation: () -> Unit) {
    val strategy = crosscutting<ComputationStrategy>()
    strategy?.execute(context, delay, computation)
}

fun Any.present(context: Any? = this, delay: Long = 0, presentation: () -> Unit) {
    val strategy = crosscutting<PresentationStrategy>()
    strategy?.execute(context, delay, presentation)
}

fun Any.networkIO(context: Any? = this, delay: Long = 0, api: () -> Unit) {
    val strategy = crosscutting<NetworkIOStrategy>()
    strategy?.execute(context, delay, api)
}

fun Any.diskIO(context: Any? = this, delay: Long = 0, operation: () -> Unit) {
    val strategy = crosscutting<DiskIOStrategy>()
    strategy?.execute(context, delay, operation)
}

fun Any.daoCrud(context: Any? = this, delay: Long = 0, crudOperation: () -> Unit) {
    val strategy = crosscutting<DaoStrategy>()
    strategy?.execute(context, delay, crudOperation)
}

fun Any.cancelAsync(context: Any = this) {
    cancelPresentations(context)
    cancelComputations(context)
    cancelNetworkIO(context)
    cancelDiskIO(context)
    cancelDaoCrud(context)
}

fun Any.cancelPresentations(context: Any = this) {
    val strategy = crosscutting<PresentationStrategy>()
    strategy?.cancel(context)
}

fun Any.cancelComputations(context: Any = this) {
    val strategy = crosscutting<ComputationStrategy>()
    strategy?.cancel(context)
}

fun Any.cancelNetworkIO(context: Any = this) {
    val strategy = crosscutting<NetworkIOStrategy>()
    strategy?.cancel(context)
}

fun Any.cancelDiskIO(context: Any = this) {
    val strategy = crosscutting<DiskIOStrategy>()
    strategy?.cancel(context)
}

fun Any.cancelDaoCrud(context: Any = this) {
    val strategy = crosscutting<DaoStrategy>()
    strategy?.cancel(context)
}