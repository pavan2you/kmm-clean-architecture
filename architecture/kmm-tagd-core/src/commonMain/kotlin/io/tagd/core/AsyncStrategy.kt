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

package io.tagd.core

import io.tagd.langx.ref.WeakReference
import kotlin.jvm.JvmName

interface AsyncExceptionHandler {

    fun asyncException(throwable: Throwable)
}

/**
 * Any class which is performing async operations, must be an AsyncContext.
 */
interface AsyncContext : Releasable

data class ExecutionContext(
    val callerContext: WeakReference<AsyncContext?>?,
    val caller: AsyncStrategy
) {

    @JvmName("notifyCaller")
    fun notify(work: (ExecutionContext) -> Unit) {
        caller.execute(context = callerContext?.get(), work = work)
    }

    override fun toString(): String {
        return "[callerContext=${callerContext?.get()}, caller=$caller]"
    }
}

interface AsyncStrategy : CrossCutting, Cancellable, Nameable {

    val exceptionHandler: AsyncExceptionHandler?

    fun execute(context: AsyncContext? = null, delay: Long = 0, work: (ExecutionContext) -> Unit)

    companion object : Releasable {

        var concurrent: AsyncStrategy? = null
        var serial: AsyncStrategy? = null

        override fun release() {
            concurrent = null
            serial = null
        }
    }
}

fun async(
    context: AsyncContext? = null,
    delay: Long = 0,
    work: (ExecutionContext) -> Unit
) {

    AsyncStrategy.concurrent!!.execute(context, delay, work)
}

fun serial(
    context: AsyncContext? = null,
    delay: Long = 0,
    work: (ExecutionContext) -> Unit
) {

    AsyncStrategy.serial!!.execute(context, delay, work)
}