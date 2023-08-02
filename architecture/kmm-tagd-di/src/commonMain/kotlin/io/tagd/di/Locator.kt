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

import io.tagd.core.Releasable
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.langx.collection.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

interface Locator : Releasable {

    val scope: Scope?

    fun layers(): Map<KClass<*>, Layer<*>?>?

    fun <T : Service> bind(layer: Layer<T>, clazz: KClass<T>)

    fun <T : Service> locate(clazz: KClass<T>): Layer<T>?
}

class LayerLocator(override var scope: Scope?) : Locator {

    private var layers: ConcurrentHashMap<KClass<*>, Layer<*>?>? = ConcurrentHashMap()

    override fun layers(): Map<KClass<*>, Layer<*>?>? = layers

    override fun <T : Service> bind(layer: Layer<T>, clazz: KClass<T>) {
        layers?.put(clazz, layer)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Service> locate(clazz: KClass<T>): Layer<T>? {
        val layer = layers?.get(clazz) ?: layers?.values?.firstOrNull {
            it?.contains(Key<T>(clazz)) ?: false
        }
        return layer as? Layer<T>?
    }

    override fun toString(): String {
        return "layers - $layers"
    }

    override fun release() {
        scope = null
        layers?.values?.forEach {
            it?.release()
        }
        layers?.clear()
        layers = null
    }
}

inline fun <reified T : Service> Locator.layer(bindings: Layer<T>.() -> Unit): Layer<T> {
    return (locate(T::class) ?: Layer(scope)).apply {
        bind(this, T::class)
        bindings()
    }
}

fun <T : Service, S : T> Locator.get(clazz: Key<S>): S? {
    return layers()
        ?.values
        ?.firstOrNull { it?.contains(clazz) ?: false }
        ?.let {
            it.get(clazz) as S?
        }
}

fun <T : Service, S : T> Locator.create(key: Key<S>, args: State? = null): S {
    var exception: Exception? = null
    var s: S? = null

    layers()
        ?.values
        ?.firstOrNull { it?.contains(key) ?: false }
        ?.let {
            @Suppress("UNCHECKED_CAST")
            try {
                s = (it as Layer<T>).create(key, args)
            } catch (e: Exception) {
                exception = e
            }
        }

    return s ?: throw exception!!
}