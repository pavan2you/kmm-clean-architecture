package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State

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

fun <T : Service, S : T> Scope.getLazy(key: Key<S>, args: State? = null): S {
    var value: S? = null
    var exception: Exception? = null

    try {
        value = locator.getLazy(key, args)
    } catch (e: Exception) {
        exception = e

        val scopes = subScopes()
        if (scopes != null) {
            for (scope in scopes) {
                try {
                    value = scope.getLazy(key, args)
                    break
                } catch (e: Exception) {
                    //ignore
                }
            }
        }
    }

    return value ?: throw exception!!
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