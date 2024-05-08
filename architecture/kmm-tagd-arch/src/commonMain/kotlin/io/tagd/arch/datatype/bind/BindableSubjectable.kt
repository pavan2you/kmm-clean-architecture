package io.tagd.arch.datatype.bind

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.core.Initializable
import io.tagd.langx.collection.concurrent.CopyOnWriteArraySet
import io.tagd.langx.ref.WeakReference

interface BindableSubjectable : Initializable, AsyncContext {

    var bindables: CopyOnWriteArraySet<WeakReference<Bindable<out BindableSubjectable>>>

    fun add(bindable: Bindable<out BindableSubjectable>)

    fun remove(bindable: Bindable<out BindableSubjectable>)

    fun removeAllBindables()

    fun addBindablesFrom(source: BindableSubjectable)

    fun switchBindingsTo(other: BindableSubjectable)

    fun notifyBindables()
}