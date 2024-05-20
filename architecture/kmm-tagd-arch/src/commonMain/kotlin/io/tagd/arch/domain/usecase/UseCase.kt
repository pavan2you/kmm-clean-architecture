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

import io.tagd.core.annotation.Visibility
import io.tagd.core.annotation.VisibleForTesting
import io.tagd.langx.Callback
import io.tagd.langx.ref.WeakReference

@Suppress("LeakingThis")
abstract class UseCase<E, T> : Command<E, T> {

    private val defaultArgs = argsOf()

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    val executionMonitor = ExecutionMonitor(WeakReference(this))

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    open fun resolveArgs(args: Args?) = args ?: defaultArgs

    protected fun resolveArgsWithMeta(args: Args?): Args {
        return resolveArgs(args).also {
            fillMetaProperties(it)
        }
    }

    private fun fillMetaProperties(args: Args) {
        args.put(ARG_USECASE, this)
    }

    override fun execute(args: Args?, success: Callback<T>?, failure: Callback<Throwable>?): E {
        return execute(args, null, success, failure)
    }

    protected abstract fun execute(
        args: Args?,
        triggering: Callback<Unit>?,
        success: Callback<T>?,
        failure: Callback<Throwable>?
    ): E

    protected fun execute(
        arguments: Args,
        success: ((T) -> Unit)?,
        existing: ResultObserver<T>?
    ) {
        when {
            existing == null -> {
                executedBy(arguments, ExecutionMonitor.ExecutionResolution.TRIGGERING)
                trigger(arguments)
            }
            existing.result != null -> {
                executedBy(arguments, ExecutionMonitor.ExecutionResolution.CACHED_RESULT)
                success?.invoke(existing.result!!)
            }
            else -> {
                executedBy(arguments, ExecutionMonitor.ExecutionResolution.IGNORING)
            }
        }
    }

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    open fun invalidate(arguments: Args) {
        executedBy(arguments, ExecutionMonitor.ExecutionResolution.INVALIDATING)
        trigger(arguments)
    }

    private fun executedBy(args: Args, value: ExecutionMonitor.ExecutionResolution) {
        executionMonitor.executedBy(args, value)
    }

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    abstract fun trigger(args: Args)

    abstract fun flush(args: Args)

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    open fun setValue(args: Args?, value : T) {
        executionMonitor.removeExecutionMonitorIfNotCacheable(args)
    }

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    open fun setError(args: Args?, throwable: Throwable) {
        executionMonitor.removeExecutionMonitor(args)
    }

    abstract fun isActive(context: Any? = null): Boolean

    override fun release() {
        executionMonitor.release()
    }

    companion object {
        const val ARG_USECASE = "usecase"
    }
}