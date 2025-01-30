package io.tagd.di

import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.core.async
import io.tagd.langx.Callback

fun <T : Service, S : T> Scope.get(clazz: Key<S>, callback: Callback<S?>) {
    async {
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
        callback.invoke(value)
    }
}

fun <T : Service, S : T> Scope.getLazy(
    key: Key<S>,
    args: State? = null,
    callback: Callback<S?>
) {

    async {
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

        value?.let {
            callback.invoke(it)
        } ?: throw exception!!
    }
}

fun <T : Service, S : T> Scope.getWithScope(clazz: Key<S>, callback: Callback<Pair<Scope?, S?>>) {
    async {
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
        callback.invoke(Pair(foundScope, value))
    }
}