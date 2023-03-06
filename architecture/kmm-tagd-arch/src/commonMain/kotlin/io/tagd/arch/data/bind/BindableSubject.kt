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

package io.tagd.arch.data.bind

import io.tagd.core.Initializable
import io.tagd.core.annotation.Visibility
import io.tagd.core.annotation.VisibleForTesting
import io.tagd.langx.Serializable
import io.tagd.langx.collection.concurrent.CopyOnWriteArraySet
import io.tagd.langx.ref.WeakReference

open class BindableSubject : Initializable, Serializable {

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    var bindables = CopyOnWriteArraySet<WeakReference<Bindable<out BindableSubject>>>()

    override fun initialize() {
        bindables = CopyOnWriteArraySet()
    }

    fun add(bindable: Bindable<out BindableSubject>) {
        remove(bindable)
        bindables.add(WeakReference(bindable))
    }

    fun remove(bindable: Bindable<out BindableSubject>) {
        bindables.firstOrNull {
            it.get() == bindable
        }?.let {
            bindables.remove(it)
        }
    }

    fun removeAllBindables() {
        bindables.clear()
    }

    @Suppress("UNCHECKED_CAST")
    fun addBindablesFrom(source: BindableSubject) {
        source.bindables.forEach {
            it.get()?.let { bindable ->
                add(bindable as Bindable<BindableSubject>)
            }
        }
    }

    fun switchBindingsTo(other: BindableSubject) {
        other.addBindablesFrom(this)
    }

    @Suppress("UNCHECKED_CAST")
    fun notifyBindables() {
        bindables.forEach {
            (it.get() as? Bindable<BindableSubject>)?.bindTo(this)
        }
    }

    companion object {
        private const val serialVersionUID: Long = 1
    }
}

