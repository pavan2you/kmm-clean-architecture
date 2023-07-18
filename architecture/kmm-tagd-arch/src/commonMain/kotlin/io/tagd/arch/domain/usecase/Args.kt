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

import io.tagd.arch.domain.usecase.Args.Companion.CONTEXT
import io.tagd.arch.domain.usecase.Args.Companion.OBSERVE
import io.tagd.core.State

open class Args(
    val observe: Boolean = true,
    val context: Any? = null
) : State() {

    companion object {
        const val OBSERVE = "observe"
        const val CONTEXT = "context"
    }
}

fun argsOf(vararg pairs: Pair<String, Any?>): Args {
    return Args(
        observe = pairs.firstOrNull { observePair -> observePair.first == OBSERVE }
            ?.let { observePair -> observePair.second as Boolean }
            ?: true,
        context = pairs.firstOrNull { contextPair -> contextPair.first == CONTEXT }?.second
    ).apply {
        val filtered = pairs.filter {
            it.first != OBSERVE || it.first != CONTEXT
        }.toTypedArray()

        putAll(filtered)
    }
}

fun argsOf(source: Args, vararg pairs: Pair<String, Any?> = arrayOf()): Args {
    return Args(
        observe = source.observe,
        context = source.context
    ).apply {
        val filteredSource = source.getAll().filter {
            it.key != OBSERVE || it.key != CONTEXT
        }.toMap()
        putAll(filteredSource)

        val filtered = pairs.filter {
            it.first != OBSERVE || it.first != CONTEXT
        }.toTypedArray()
        putAll(filtered)
    }
}