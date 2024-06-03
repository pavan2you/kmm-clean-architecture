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

package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State

inline fun <reified T : Service, reified S : T> Scope.bind(key: Key<S>? = null, instance: S) {
    val keyDerived = key ?: key()
    layer<T> {
        bind(service = keyDerived, instance = instance)
    }
}

inline fun <reified T : Service, reified S : T> Scope.bindLazy(
    key: Key<S>? = null,
    noinline creator: (State?) -> S
) {

    val keyDerived = key ?: key()
    layer<T> {
        bindLazy(service = keyDerived, creator = creator)
    }
}

fun <T : Service, S : T> Scope.create(key: Key<S>, args: State? = null): S {
    var value: S? = null
    var exception: Exception? = null

    try {
        value = locator.create(key, args)
    } catch (e: Exception) {
        exception = e

        val scopes = subScopes()
        if (scopes != null) {
            for (scope in scopes) {
                try {
                    value = scope.create(key, args)
                    break
                } catch (e: Exception) {
                    //ignore
                }
            }
        }
    }

    return value ?: throw exception!!
}