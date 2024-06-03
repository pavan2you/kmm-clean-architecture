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

import io.tagd.core.Nameable
import io.tagd.core.Releasable
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.Scope.Companion.GLOBAL_SCOPE
import io.tagd.langx.IllegalAccessException
import io.tagd.langx.collection.concurrent.ConcurrentHashMap

open class Scope(name: String = GLOBAL_SCOPE) : Nameable,
    Releasable {

    final override var name: String = name
        private set

    private var mutableLocator: Locator? = LayerLocator(this)
    private var scopes: ConcurrentHashMap<String, Scope>? = ConcurrentHashMap()
    private var mutableState: State? = State()

    val locator: Locator
        get() = mutableLocator!!

    val state: State
        get() = mutableState!!

    fun updateName(name: String) {
        this.name = name
    }

    fun with(locator: Locator): Scope {
        release()
        mutableLocator = locator
        return this
    }

    fun with(state: State): Scope {
        mutableState?.release()
        mutableState = state
        return this
    }

    fun addSubScope(scope: Scope): Scope {
        if (scope.name == GLOBAL_SCOPE) {
            throw IllegalAccessException("global scope can not be a sub scope")
        }
        if (scopes?.contains(scope.name) == true) {
            throw IllegalAccessException("${this.name} already having ${scope.name}")
        }
        scopes?.put(scope.name, scope)
        return scope
    }

    fun addSubScopeIfAbsent(scope: Scope): Scope {
        if (scope.name == GLOBAL_SCOPE) {
            throw IllegalAccessException("global scope can not be a sub scope")
        }

        val foundScope: Scope? = scopes?.get(scope.name)
        if (foundScope != null && foundScope !== scope) {
            throw IllegalAccessException("${this.name} already having ${scope.name}")
        } else {
            scopes?.put(scope.name, scope)
        }
        return scope
    }

    fun addSubScopeIfAbsent(scopeName: String): Scope {
        if (scopeName == GLOBAL_SCOPE) {
            throw IllegalAccessException("global scope can not be a sub scope")
        }

        var scope: Scope? = scopes?.get(scopeName)
        if (scope == null) {
            scope = Scope(scopeName)
            scopes?.put(scopeName, scope)
        }
        return scope
    }

    fun subScopes(): Collection<Scope>? {
        return scopes?.values
    }

    fun subScope(name: String): Scope? {
        var value = scopes?.get(name)
        if (value == null) {
            val scopes = subScopes()
            if (scopes != null) {
                for (scope in scopes) {
                    value = scope.subScope(name)
                    if (value != null) {
                        break
                    }
                }
            }
        }
        return value
    }

    fun removeSubScope(name: String): Scope? {
        var value = scopes?.remove(name)
        if (value == null) {
            val scopes = subScopes()
            if (scopes != null) {
                for (scope in scopes) {
                    value = scope.removeSubScope(name)
                    if (value != null) {
                        break
                    }
                }
            }
        }
        value?.release()
        return value
    }

    fun getSubScope(name: String): Scope? {
        return scopes?.remove(name)
    }

    open fun reset() {
        releaseSubScopes()
        mutableLocator = LayerLocator(this)
        mutableState = State()
    }

    override fun toString(): String {
        return name
    }

    override fun release() {
        releaseSubScopes()
        scopes = null

        mutableLocator?.release()
        mutableLocator = null

        mutableState?.release()
        mutableState = null
    }

    private fun releaseSubScopes() {
        subScopes()?.forEach {
            it.release()
        }
        scopes?.clear()
    }

    companion object {
        const val GLOBAL_SCOPE = "global"
    }
}

fun scope(name: String, parent: Scope? = Global, bindings: Scope.() -> Unit): Scope {
    val scope = if (name == GLOBAL_SCOPE) {
        if (parent == null || parent == Global) {
            Global
        } else {
            throw IllegalAccessException("global scope can not be a sub scope")
        }
    } else {
        (parent ?: Global).addSubScopeIfAbsent(name)
    }
    return scope.apply {
        bindings()
    }
}

fun Scope.inject(bindings: Scope.() -> Unit): Scope {
    bindings()
    return this
}

inline fun <reified T : Service> Scope.layer(bindings: Layer<T>.() -> Unit): Layer<T> {
    return locator.layer(bindings)
}

