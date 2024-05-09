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

package io.tagd.android.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.tagd.arch.control.IApplication
import io.tagd.arch.datatype.DataObjectable
import io.tagd.arch.datatype.bind.BindableSubjectable
import io.tagd.arch.present.mvb.AdaptableBindableView
import io.tagd.arch.present.mvb.BindableView
import io.tagd.arch.present.mvb.Binder

abstract class BindableRecyclerViewHolder<
    T : BindableSubjectable,
    V : AdaptableBindableView<T>,
    B : Binder<T, V>
> : RecyclerView.ViewHolder, AdaptableBindableView<T> {

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

        //no-op
    }

    constructor(
        inflater: LayoutInflater,
        layoutResId: Int,
        parent: ViewGroup
    ) : this(inflater.inflate(layoutResId, parent, false)) {

        //no-op
    }

    constructor(
        parent: ViewGroup,
        layoutResId: Int
    ) : this(View.inflate(parent.context, layoutResId, parent)) {

        //no-op
    }

    override fun onCreate() {
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

    override fun attached() {
        //no op
    }

    override fun detached() {
        //no op
    }

    override fun recycled() {
        //no op
    }

    override fun onDestroy() {
        release()
    }

    override fun release() {
        unbind()

        binder?.onDestroy()
        binder = null

        model?.remove(this)
        model = null
    }
}