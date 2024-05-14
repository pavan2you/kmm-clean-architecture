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

abstract class Value<T : Service> {

    abstract fun get(args: State? = null): T
}

class GetValue<T : Service>(private var value: T): Value<T>() {

    override fun get(args: State?): T {
        return value
    }

    override fun toString(): String {
        return "$value"
    }
}

class CreateValue<T : Service>(private val creator: (State?) -> T): Value<T>() {

    override fun get(args: State?): T {
        return creator.invoke(args)
    }

    override fun toString(): String {
        return "$creator"
    }
}

class LazyValue<T : Service>(private val creator: (State?) -> T): Value<T>() {

    private lateinit var value: T

    override fun get(args: State?): T {
        if (!this::value.isInitialized) {
            value = creator.invoke(args)
        }
        return value
    }

    override fun toString(): String {
        return if (!this::value.isInitialized) {
            "$creator"
        } else {
            "$value"
        }
    }
}