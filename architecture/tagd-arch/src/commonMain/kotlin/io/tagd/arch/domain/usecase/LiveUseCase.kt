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
import io.tagd.langx.collection.concurrent.ConcurrentHashMap

abstract class LiveUseCase<T> : UseCase<Unit, T>() {

    private val liveObservers = ConcurrentHashMap<Args, LiveData<T>>()

    override fun execute(
        args: Args?,
        triggering: Callback<Unit>?,
        success: Callback<T>?,
        failure: Callback<Throwable>?
    ) {
        val (arguments, existing) = resolveArgsAndObserver(
            args,
            triggering,
            success,
            failure
        )

        triggering?.invoke(Unit)
        execute(arguments, success, existing)
    }

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    fun resolveArgsAndObserver(
        args: Args?,
        triggering: Callback<Unit>?,
        success: Callback<T>?,
        failure: Callback<Throwable>?
    ): Pair<Args, LiveObserver<T>?> {

        val arguments = resolveArgsWithMeta(args)
        val observer: LiveObserver<T> = LiveObserver(arguments, triggering, success, failure)
        val existing = putIfAbsent(arguments, observer)
        return Pair(arguments, existing)
    }

    private fun putIfAbsent(
        args: Args,
        observer: LiveData.Observer<T>
    ): LiveObserver<T>? {

        var liveData = liveObservers[args]
        if (liveData == null) {
            liveData = LiveData(args)
            liveObservers[args] = liveData
        }
        return liveData.putIfAbsent(observer) as? LiveObserver<T>
    }

    fun invalidate() {
        liveObservers.forEach {
            it.value.dispatchInvalidate()
            invalidate(it.key)
        }
    }

    override fun flush(args: Args) {
        removeObserver(args)
    }

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    override fun setValue(args: Args?, value: T) {
        liveData(args)?.setValue(value)
        super.setValue(args, value)
    }

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    override fun setError(args: Args?, throwable: Throwable) {
        liveData(args)?.setError(throwable)
        removeObserver(args)
        super.setError(args, throwable)
    }

    override fun lastResult(args: Args?): T? {
        return liveData(args)?.value
    }

    override fun cancel(context: Any?): Boolean {
        if (context == null) {
            cancelObservers(liveObservers.keys.toList())
        } else {
            cancelObservers(liveObservers.keys.toList().filter {
                it.context == context
            })
        }
        return !isActive(context)
    }

    private fun cancelObservers(list: Iterable<Args?>) {
        list.forEach { args ->
            removeObserver(args)
            val arguments = resolveArgs(args)
            executionMonitor.remove(arguments)
        }
    }

    private fun removeObserver(args: Args?) {
        val arguments = resolveArgs(args)
        liveData(arguments)?.release()
        liveObservers.remove(arguments)
    }

    private fun liveData(args: Args?): LiveData<T>? {
        val arguments = resolveArgs(args)
        return liveObservers[arguments]
    }

    override fun isActive(context: Any?): Boolean {
        return if (context == null) {
            liveObservers.size > 0
        } else {
            liveObservers.keys.toList().any {
                it.context == context
            }
        }
    }

    override fun release() {
        cancel(null)
        liveObservers.clear()
        super.release()
    }
}