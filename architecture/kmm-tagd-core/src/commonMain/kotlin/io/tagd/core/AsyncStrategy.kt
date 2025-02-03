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

/**
 * Concurrency frameworks and its deep roots to domain logic is a very
 * questionable design decision made, Like the DI frameworks, the next big domain logic polluter is
 * a concurrent framework choice made. The domain logic, to that matter, the any application logic
 * must be concurrent framework free. What it means is -- **Separate triggers from logic**.
 * Concurrent frameworks / APIs must limit to just as logic execution triggers, nothing beyond that.
 *
 * Imagine a codebase which has humongous amount of code which is spread across 100s of modules and
 * assume concurrent flows are coupled to a concurrent framework A, after some months/years, there
 * is new concurrent framework(let's say framework B) in the market, which appears like a wow and
 * fascinating then switching from framework A to B comes up with lot of cost, otherwise one must
 * find a way to patch it through some inter operable adapters.
 *
 * The very question to ask is, can not we design the logic without these magical frameworks? and
 * limit frameworks polluting the domain/infra logic?
 *
 * The ans to this is the [AsyncStrategy]. It encourages to obscure the what framework choice you
 * made. Your domain/infra logic is free. You can switch from framework A/B, through a matter of
 * setter. Wouldn't that be cool? The cost to refactor from 1000s of lines a single line of setter
 * and by not even touching the single logic.
 */
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

typealias AsyncOn = (AsyncContext, Long, (ExecutionContext) -> Unit) -> Unit

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