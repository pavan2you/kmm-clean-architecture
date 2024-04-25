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

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.arch.domain.crosscutting.async.ObserveOn
import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.core.Initializable
import io.tagd.core.annotation.Visibility
import io.tagd.core.annotation.VisibleForTesting
import io.tagd.langx.datatype.Serializable
import io.tagd.langx.collection.concurrent.CopyOnWriteArraySet
import io.tagd.langx.ref.WeakReference
import kotlin.jvm.Transient

interface BindableSubjectable : Initializable, Serializable, AsyncContext {

    var bindables: CopyOnWriteArraySet<WeakReference<Bindable<out BindableSubjectable>>>

    fun add(bindable: Bindable<out BindableSubjectable>)

    fun remove(bindable: Bindable<out BindableSubjectable>)

    fun removeAllBindables()

    fun addBindablesFrom(source: BindableSubjectable)

    fun switchBindingsTo(other: BindableSubjectable)

    fun notifyBindables()
}

open class BindableSubject : BindableSubjectable {

    @VisibleForTesting(otherwise = Visibility.PRIVATE)
    @Transient
    override var bindables = CopyOnWriteArraySet<WeakReference<Bindable<out BindableSubjectable>>>()

    override fun initialize() {
        bindables = CopyOnWriteArraySet()
    }

    override fun add(bindable: Bindable<out BindableSubjectable>) {
        remove(bindable)
        bindables.add(WeakReference(bindable))
    }

    override fun remove(bindable: Bindable<out BindableSubjectable>) {
        bindables.firstOrNull {
            it.get() == bindable
        }?.let {
            bindables.remove(it)
        }
    }

    override fun removeAllBindables() {
        bindables.clear()
    }

    @Suppress("UNCHECKED_CAST")
    override fun addBindablesFrom(source: BindableSubjectable) {
        source.bindables.forEach {
            it.get()?.let { bindable ->
                add(bindable as Bindable<BindableSubject>)
            }
        }
    }

    override fun switchBindingsTo(other: BindableSubjectable) {
        other.addBindablesFrom(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun notifyBindables() {
        bindables.forEach {
            (it.get() as? Bindable<BindableSubject>)?.bindTo(this)
        }
    }

    protected fun ObserveOn?.notifyObservers(context: AsyncContext = this@BindableSubject) {
        this?.invoke(context, 0) {
            notifyBindables()
        } ?: kotlin.run {
            notifyBindables()
        }
    }

    override fun release() {
        cancelAsync()
    }

    companion object {
        private const val serialVersionUID: Long = 1
    }
}

