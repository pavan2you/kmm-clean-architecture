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

package io.tagd.arch.test

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.arch.domain.crosscutting.async.AsyncExceptionHandler
import io.tagd.arch.domain.crosscutting.async.AsyncStrategy
import io.tagd.arch.domain.crosscutting.async.CacheIOStrategy
import io.tagd.arch.domain.crosscutting.async.ComputationStrategy
import io.tagd.arch.domain.crosscutting.async.ComputeIOStrategy
import io.tagd.arch.domain.crosscutting.async.DaoIOStrategy
import io.tagd.arch.domain.crosscutting.async.DiskIOStrategy
import io.tagd.arch.domain.crosscutting.async.NetworkIOStrategy
import io.tagd.arch.domain.crosscutting.async.PresentationStrategy

open class FakeAsyncStrategy : AsyncStrategy, PresentationStrategy, ComputationStrategy,
    ComputeIOStrategy, NetworkIOStrategy, DiskIOStrategy, DaoIOStrategy, CacheIOStrategy {

    var released: Boolean = false

    override val exceptionHandler: AsyncExceptionHandler?
        get() = FakeAsyncExceptionHandler()

    override fun execute(context: AsyncContext?, delay: Long, work: () -> Unit) {
        work.invoke()
    }

    override fun release() {
        released = true
    }

    override fun cancel(context: Any?): Boolean {
        return true
    }
}