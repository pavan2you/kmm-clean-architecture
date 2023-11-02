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

package io.tagd.arch.control

import io.tagd.arch.infra.InfraService
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.Scope
import io.tagd.di.get

interface AppService : InfraService

inline fun <reified S : AppService> appService(key: Key<S>? = null): S? {
    return Global.get<AppService, S>(key ?: io.tagd.di.key())
}

inline fun <reified S : AppService> Scope.appService(key: Key<S>? = null): S? {
    return this.get<AppService, S>(key ?: io.tagd.di.key())
}