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

@VisibleForTesting(otherwise = Visibility.PACKAGE_PRIVATE)
class LiveObserver<T>(
    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    override val args: Args? = null,

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    val invalidating: Callback<Unit>? = null,

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    override val success: Callback<T>? = null,

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    override val failure: Callback<Throwable>? = null
) : ResultObserver<T>(args, success, failure), LiveData.Observer<T> {

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    var resultVersion = NO_DATA_VERSION

    override fun onInvalidating() {
        invalidating?.invoke(Unit)
    }

    override fun onChange(value: T, version: Int) {
        if (resultVersion < version) {
            resultVersion = version
            setValue(value)
        }
    }

    override fun setValue(value: T) {
        if (args == null || args.observe) {
            result = value
        }
        success?.invoke(value)
    }

    override fun onError(error: Throwable) {
        setError(error)
    }

    companion object {
        private const val NO_DATA_VERSION = -1
    }
}