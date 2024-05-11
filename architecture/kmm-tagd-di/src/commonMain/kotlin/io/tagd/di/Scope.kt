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

open class Scope(override val name: String = GLOBAL_SCOPE) : Nameable,
    Releasable {

    private var mutableLocator: Locator? = LayerLocator(this)
    private var scopes: ConcurrentHashMap<String, Scope>? = ConcurrentHashMap()
    private var mutableState: State? = State()

    val locator: Locator
        get() = mutableLocator!!

    val state: State
        get() = mutableState!!

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
        return addSubScopeIfAbsent(scope.name)
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

inline fun <reified T : Service> Scope.layer(bindings: Layer<T>.() -> Unit): Layer<T> {
    return locator.layer(bindings)
}

fun <T : Service, S : T> Scope.get(clazz: Key<S>): S? {
    var value: S? = locator.get(clazz)
    if (value == null) {
        val scopes = subScopes()
        if (scopes != null) {
            for (scope in scopes) {
                value = scope.get(clazz)
                if (value != null) {
                    break
                }
            }
        }
    }
    return value
}

fun <T : Service, S : T> Scope.getWithScope(clazz: Key<S>): Pair<Scope?, S?> {
    var value: S? = locator.get(clazz)
    var foundScope: Scope? = this

    if (value == null) {
        val scopes = subScopes()
        if (scopes != null) {
            for (scope in scopes) {
                val valueWithScope = scope.getWithScope(clazz)
                if (valueWithScope.second != null) {
                    foundScope = valueWithScope.first
                    value = valueWithScope.second
                    break
                }
            }
        }
    }
    return Pair(foundScope, value)
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

inline fun <reified T : Service, reified S : T> Scope.bind(key: Key<S>? = null, instance: S) {
    val keyDerived = key ?: key()
    layer<T> {
        bind(service = keyDerived, instance = instance)
    }
}

inline fun <reified T : Service, reified S : T> scopeOf(key: Key<S>? = null): Scope? {
    val keyDerived = key ?: key()

    var value: S? = Global.locator.get(keyDerived)
    var valueScope: Scope? = null

    if (value == null) {
        val scopes = Global.subScopes()
        if (scopes != null) {
            for (scope in scopes) {
                value = scope.get(keyDerived)
                if (value != null) {
                    valueScope = scope
                    break
                }
            }
        }
    } else {
        valueScope = Global
    }
    return valueScope
}

object Global : Scope() {

    fun <T : DependentService> dependsOn(
        dependent: T,
        influencers: List<Key<out Service>>
    ) {
        DependencyHandler.dependsOn(dependent, influencers)
    }

    override fun reset() {
        super.reset()
        DependencyHandler.reset()
    }

    override fun release() {
        DependencyHandler.release()
        super.release()
    }
}

interface Scopable : Service, Nameable {

    val outerScope: Scope

    val thisScope: Scope
}