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

package io.tagd.droid.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.tagd.arch.control.IApplication
import io.tagd.arch.data.DataObject
import io.tagd.arch.present.mvb.BindableView
import io.tagd.arch.present.mvb.Binder

abstract class BindableRecyclerViewHolder<T : DataObject, V : BindableView<T>, B : Binder<T, V>> :
    RecyclerView.ViewHolder, BindableView<T> {

    override val app: IApplication?
        get() = itemView.context?.applicationContext as? IApplication

    protected var binder: B? = null

    override var model: T? = null
        set(value) {
            field?.remove(this)
            field = value
            value?.add(this)
        }

    private lateinit var optionals: Array<out Any?>

    private var unbound: Boolean = false

    constructor(
        view: View
    ) : super(view) {

        initialize()
    }

    constructor(
        inflater: LayoutInflater,
        layoutResId: Int,
        parent: ViewGroup
    ) : this(inflater.inflate(layoutResId, parent, false)) {

        initialize()
    }

    constructor(
        parent: ViewGroup,
        layoutResId: Int
    ) : this(View.inflate(parent.context, layoutResId, parent)) {

        initialize()
    }

    private fun initialize() {
        onCreate()
    }

    protected open fun onCreate() {
        binder = onCreateBinder()
        binder?.onCreate()
        onCreateView()
    }

    protected abstract fun onCreateBinder(): B?

    protected abstract fun onCreateView()

    override fun show() {
        itemView.visibility = View.VISIBLE
    }

    override fun hide() {
        itemView.visibility = View.INVISIBLE
    }

    override fun binder(): Binder<T, out BindableView<T>>? {
        return binder
    }

    override fun bindTo(subject: T) {
        bind(subject, *optionals)
    }

    open fun bind(model: T, vararg optionals: Any?) {
        this.optionals = optionals
        binder?.onBind(model, *optionals)
    }

    open fun unbind() {
        if (!unbound) {
            unbound = true
            binder?.onUnbind()
        }
    }

    open fun attached() {
        //no op
    }

    open fun detached() {
        //no op
    }

    open fun recycled() {
        //no op
    }

    fun onDestroy() {
        release()
    }

    override fun release() {
        unbind()
        binder?.onDestroy()
        binder = null
        model = null
    }
}