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
import io.tagd.di.Scope.Companion.GLOBAL_SCOPE
import io.tagd.langx.IllegalAccessException
import io.tagd.langx.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T : Service> inject(
    scope: String = GLOBAL_SCOPE,
    key: Key<T> = Key(typeOf<T>())
): InjectDelegateProvider<T> {

    val resolvedScope = resolveScope(scope)
    return InjectDelegateProvider(resolvedScope, key)
}

inline fun <reified T : Service> injectX(
    scope: String = GLOBAL_SCOPE,
    key: Key<T> = Key(typeOf<T>())
): NullableInjectDelegateProvider<T> {

    val resolvedScope = resolveScope(scope)
    return NullableInjectDelegateProvider(resolvedScope, key)
}

fun resolveScope(scope: String): Scope {
    return if (scope != GLOBAL_SCOPE) {
        Global.subScope(scope) ?: Global
    } else Global
}

class InjectDelegateProvider<T : Service>(private val scope: Scope, private val key: Key<T>) {

    operator fun provideDelegate(thisRef: Any, prop: KProperty<*>): InjectDelegate<T> {
        return InjectDelegate(scope, key)
    }
}

class InjectDelegate<T : Service>(private val scope: Scope, private val key: Key<T>) {

    private var valueReference: WeakReference<T?>? = null

    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getValue() ?: throw IllegalAccessException(
            "The dependency is not available for ${property.name}"
        )
    }

    private fun getValue(): T? {
        var value = valueReference?.get()
        if (value == null) {
            value = scope.get(key)
            valueReference = WeakReference(value)
        }
        return value
    }
}

class NullableInjectDelegateProvider<T : Service>(
    private val scope: Scope,
    private val key: Key<T>
) {

    operator fun provideDelegate(thisRef: Any, prop: KProperty<*>): NullableInjectDelegate<T> {
        return NullableInjectDelegate(scope, key)
    }
}

class NullableInjectDelegate<T : Service>(private val scope: Scope, private val key: Key<T>) :
    ReadWriteProperty<Any, T?> {

    private var released: Boolean = false
    private var valueReference: WeakReference<T?>? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return if (released) {
            throw IllegalAccessException("Accessing released ${property.name}")
        } else {
            getValue() ?: throw IllegalAccessException(
                "The dependency for ${property.name} is not available"
            )
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        if (value == null) {
            valueReference?.clear()
            valueReference = null
            released = true
        } else {
            throw IllegalAccessException(
                "The dependency for ${property.name} is " +
                        "read-only nullable, call site can nullify but not alter the value"
            )
        }
    }

    private fun getValue(): T? {
        var value = valueReference?.get()
        if (value == null) {
            value = scope.get(key)
            valueReference = WeakReference(value)
        }
        return value
    }
}