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

package io.tagd.arch.domain.usecase

import io.tagd.core.Releasable
import io.tagd.core.annotation.Visibility
import io.tagd.core.annotation.VisibleForTesting
import io.tagd.langx.collection.concurrent.ConcurrentHashMap
import io.tagd.langx.ref.WeakReference

@VisibleForTesting(otherwise = Visibility.PACKAGE_PRIVATE)
class ExecutionMonitor<E, T>(private val useCaseRef: WeakReference<UseCase<E, T>>) : Releasable {

    private val useCase
        get() = useCaseRef.get()

    private val monitors = ConcurrentHashMap<Args?, ExecutionResolution>()

    fun isLastExecutionIgnored(args: Args?): Boolean {
        return resolutionFor(args) == ExecutionResolution.IGNORING
    }

    fun isLastExecutionFromCache(args: Args?): Boolean {
        return resolutionFor(args) == ExecutionResolution.CACHED_RESULT
    }

    fun isLastExecutionFromTrigger(args: Args?): Boolean {
        return resolutionFor(args) == ExecutionResolution.TRIGGERING
    }

    fun isLastExecutionFromInvalidation(args: Args?): Boolean {
        return resolutionFor(args) == ExecutionResolution.INVALIDATING
    }

    fun isHavingExecutionMonitor(args: Args?): Boolean {
        return resolutionFor(args) != null
    }

    fun executedBy(args: Args, value: ExecutionResolution) {
        monitors[args] = value
    }

    private fun resolutionFor(args: Args?): ExecutionResolution? {
        val arguments = useCase?.resolveArgs(args)
        return monitors[arguments]
    }

    fun removeExecutionMonitorIfNotCacheable(args: Args?) {
        if (args?.observe != true) {
            removeExecutionMonitor(args)
        }
    }

    fun removeExecutionMonitor(args: Args?) {
        useCase?.resolveArgs(args)?.let { remove(it) }
    }

    fun remove(args: Args) {
        monitors.remove(args)
    }

    override fun release() {
        monitors.clear()
        useCaseRef.clear()
    }

    @VisibleForTesting(otherwise = Visibility.PACKAGE_PRIVATE)
    enum class ExecutionResolution {
        TRIGGERING, IGNORING, CACHED_RESULT, INVALIDATING
    }
}