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

import io.tagd.arch.domain.usecase.Args
import io.tagd.langx.Callback

class FakeUseCaseCaller<T>(val args: Args? = Args()) {

    var triggered: Boolean = false
    var result: T? = null
    var error: Throwable? = null

    val triggering: Callback<Unit> = {
        triggered = true
    }

    val success: Callback<T> = {
        result = it
    }

    val failure: Callback<Throwable> = {
        error = it
    }
}