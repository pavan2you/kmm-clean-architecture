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

package io.tagd.kotlinx.coroutines

import kotlinx.coroutines.CoroutineDispatcher

class Dispatchers private constructor() {


    lateinit var Main: CoroutineDispatcher
        private set

    lateinit var Computation: CoroutineDispatcher
        private set

    lateinit var IO: CoroutineDispatcher
        private set

    lateinit var DaoIO: CoroutineDispatcher
        private set

    lateinit var Unconfined: CoroutineDispatcher
        private set

    class Builder {

        private val cooking = Dispatchers()

        fun Main(dispatcher: CoroutineDispatcher): Builder {
            cooking.Main = dispatcher
            return this
        }

        fun IO(dispatcher: CoroutineDispatcher): Builder {
            cooking.IO = dispatcher
            return this
        }

        fun DaoIO(dispatcher: CoroutineDispatcher): Builder {
            cooking.DaoIO = dispatcher
            return this
        }

        fun Computation(dispatcher: CoroutineDispatcher): Builder {
            cooking.Computation = dispatcher
            return this
        }

        fun Unconfined(dispatcher: CoroutineDispatcher): Builder {
            cooking.Unconfined = dispatcher
            return this
        }

        fun build(): Dispatchers {
            return cooking
        }
    }

    companion object {

        @JvmStatic
        private var active: Dispatchers? = null

        @JvmStatic
        fun set(provider: Dispatchers) {
            active = provider
        }

        @JvmStatic
        fun get(): Dispatchers = active
            ?: throw IllegalAccessException("call `Dispatchers.set` before accessing")

        val Main
            get() = try {
                get().Main
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.Main
            }

        val Computation
            get() = try {
                get().Computation
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.Computation
            }

        val IO
            get() = try {
                get().IO
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.IO
            }

        val DaoIO
            get() = try {
                get().DaoIO
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.DaoIO
            }

        val Unconfined
            get() = try {
                get().Unconfined
            } catch (e: Exception) {
                kotlinx.coroutines.Dispatchers.Unconfined
            }
    }
}