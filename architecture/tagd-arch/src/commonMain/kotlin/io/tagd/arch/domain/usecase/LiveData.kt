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
import io.tagd.langx.collection.concurrent.CopyOnWriteArrayList
import io.tagd.langx.ref.concurrent.atomic.AtomicReference

class LiveData<T>(private val args: Args?) : Releasable {

    interface Observer<T> {

        fun onInvalidating()

        fun onChange(value: T, version: Int)

        fun onError(error: Throwable)
    }

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    val observers = CopyOnWriteArrayList<Observer<T>>()

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    var value: T? = null
        private set

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    var valueVersion = NO_DATA_VERSION
        private set

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    val invalidate = AtomicReference(false)

    fun setValue(value: T) {
        this.value = value
        valueVersion++

        notifyObservers(value, valueVersion)
    }

    private fun notifyObservers(value: T, version: Int) {
        observers.let {
            do {
                invalidate.set(false)
                notifyObservers(it, value, version)
            } while (invalidate.get())

            if (args?.observe == false) {
                it.clear()
            }
        }
    }

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    fun notifyObservers(
        list: CopyOnWriteArrayList<Observer<T>>,
        value: T,
        version: Int
    ) {
        val observers = list.asArray(arrayOf<Observer<T>>())
        for (observer in observers) {
            observer.onChange(value, version)
            if (invalidate.get()) {
                break
            }
        }
    }

    fun setError(error: Throwable) {
        observers.let {
            for (observer in it) {
                observer.onError(error)
            }
        }
    }

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    fun addObserver(observer: Observer<T>) {
        observers.add(observer)
        invalidate.set(true)
    }

    fun removeObserver(observer: Observer<T>) {
        observers.remove(observer)
    }

    fun putIfAbsent(observer: Observer<T>): Observer<T>? {
        val pastValue = observers.firstOrNull {
            it == observer
        }
        if (pastValue == null) {
            addObserver(observer)
        }
        return pastValue
    }

    fun removeAll() {
        observers.clear()
    }

    fun dispatchInvalidate() {
        for (observer in observers) {
            observer.onInvalidating()
        }
    }

    override fun release() {
        removeAll()
        invalidate.set(false)
        value = null
        valueVersion = NO_DATA_VERSION
    }

    companion object {
        private const val NO_DATA_VERSION = -1
    }
}