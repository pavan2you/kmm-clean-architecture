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
import io.tagd.langx.IllegalAccessException
import io.tagd.langx.collection.concurrent.CopyOnWriteArrayList
import io.tagd.langx.collection.concurrent.removeAllByFilter
import io.tagd.langx.ref.concurrent.atomic.AtomicBoolean

abstract class CallableUseCase<T> : UseCase<Unit, T>() {

    private var cancelled: AtomicBoolean = AtomicBoolean(false)
    private var cancelledContexts = CopyOnWriteArrayList<String>()

    private val observers = CopyOnWriteArrayList<ResultObserver<T>?>()

    override fun execute(
        args: Args?,
        triggering: Callback<Unit>?,
        success: Callback<T>?,
        failure: Callback<Throwable>?
    ) {
        val arguments = resolveArgsWithMeta(args)
        cancelledContexts.remove(arguments.context as? String)
        cancelled.set(false)

        val observer: ResultObserver<T>? = ResultObserver(arguments, success, failure)
        val existing = putIfAbsent(observer)

        triggering?.invoke(Unit)
        execute(arguments, success, existing)
    }

    private fun putIfAbsent(observer: ResultObserver<T>?): ResultObserver<T>? {
        val existing = observers.lastOrNull {
            it == observer
        }
        if (existing == null) {
            observers.add(observer)
        }
        return existing
    }

    override fun invalidate(arguments: Args) {
        throw IllegalAccessException(
            "Callable is only for one shot usage, so not possible to invalidate"
        )
    }

    override fun flush(args: Args) {
        removeObserver(args)
    }

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    override fun setValue(args: Args?, value : T) {
        if (!cancelled.get()) {
            val arguments = resolveArgs(args)
            notifyValue(arguments, value)
            removeAllButRecentResult(arguments)
        }
        super.setValue(args, value)
    }

    private fun notifyValue(arguments: Args, value: T) {
        observers.filter {
            //filter the only result awaiting observers.
            it?.args == arguments && it.result == null
        }.forEach {
            it?.setValue(value)
        }
    }

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    override fun setError(args: Args?, throwable: Throwable) {
        if (!cancelled.get()) {
            val arguments = resolveArgs(args)
            notifyError(arguments, throwable)
            removeObserver(arguments)
        }
        super.setError(args, throwable)
    }

    private fun notifyError(arguments: Args, throwable: Throwable) {
        observers.filter {
            //filter the only result awaiting observers.
            it?.args == arguments && it.result == null
        }.forEach {
            it?.setError(throwable)
        }
    }

    private fun removeAllButRecentResult(arguments: Args) {
        val mostRecent = observers.lastOrNull {
            it?.args == arguments && it.result != null
        }
        observers.removeAllByFilter { it?.args == arguments }
        if (arguments.observe && mostRecent != null) {
            observers.add(mostRecent)
        }
    }

    private fun removeObserver(arguments: Args?) {
        observers.removeAllByFilter { it?.args == arguments }
    }

    override fun lastResult(args: Args?): T? {
        val arguments = resolveArgs(args)
        return observers.firstOrNull {
            it?.args == arguments
        }?.result
    }

    override fun cancel(context: Any?): Boolean {
        if (context == null) {
            cancelledContexts.clear()
            cancelled.set(true)
            observers.clear()
        } else {
            observers.removeAll {
                it?.args?.context == context
            }
        }
        return true
    }

    override fun isActive(context: Any?): Boolean {
        return if (context == null) {
            observers.size > 0
        } else {
            observers.any {
                it?.args?.context == context
            }
        }
    }

    override fun release() {
        cancelled.set(true)
        observers.clear()
    }
}