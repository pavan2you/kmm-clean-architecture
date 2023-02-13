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

import io.tagd.core.Cancellable
import io.tagd.core.Service

typealias Callback<T> = (T) -> Unit

interface Command<EXECUTE, T> : Cancellable, Service {

    fun execute(
        args: Args? = null,
        success: Callback<T>? = null,
        failure: Callback<Throwable>? = null
    ): EXECUTE

    fun lastResult(args: Args? = null): T?
}