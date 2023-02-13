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

/**
 * A unified state passing mechanism across the architecture objects, where the argument
 * sequence would be 1 to N. Instead of various overloaded methods, this would be considered as
 * an alternative.
 */
open class State : Releasable {

    private val map = mutableMapOf<String, Any?>()

    /**
     * Put an argument's key, value in State.
     */
    fun put(key: String, value: Any?) {
        map[key] = value
    }

    /**
     * Access an argument by its key.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? {
        return map[key] as T?
    }

    /**
     * Put all arguments key, value pairs in State.
     */
    fun putAll(pairs: Array<out Pair<String, Any?>>) {
        map.putAll(pairs)
    }

    /**
     * find out whether the state is empty
     */
    fun isEmpty() = map.isEmpty()

    /**
     * find out clear
     */
    fun clear() {
        map.clear()
    }

    override fun release() {
        clear()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        if (map != other.map) return false

        return true
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }

    override fun toString(): String {
        return "State(map=$map)"
    }
}

/**
 * A factory method to create a State with give key-value [Pair]s.
 */
fun stateOf(vararg pairs: Pair<String, Any?>): State {
    return State().apply {
        putAll(pairs)
    }
}