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

import androidx.recyclerview.widget.RecyclerView
import io.tagd.arch.data.DataObject
import io.tagd.core.Releasable

abstract class ReleasableRecyclerAdapter<T : DataObject, VH : BindableRecyclerViewHolder<T, *, *>> :
    RecyclerView.Adapter<VH>(), Releasable {

    val items: MutableList<T> = mutableListOf()
    private var recyclerView: RecyclerView? = null

    open fun setDataModel(list: List<T>) {
        this.items.clear()
        this.items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        release()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.attached()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        holder.detached()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewRecycled(holder: VH) {
        holder.recycled()
        super.onViewRecycled(holder)
    }

    override fun release() {
        releaseViewHolders(recyclerView)
        items.clear()
        recyclerView = null
    }

    private fun releaseViewHolders(recyclerView: RecyclerView?) {
        recyclerView?.layoutManager?.let { layoutManager ->
            val viewCount = layoutManager.childCount
            for (index in 0..viewCount) {
                layoutManager.getChildAt(index)?.let {
                    recyclerView.getChildViewHolder(it)?.let { viewHolder ->
                        (viewHolder as BindableRecyclerViewHolder<*, *, *>).onDestroy()
                    }
                }
            }
        }
    }
}