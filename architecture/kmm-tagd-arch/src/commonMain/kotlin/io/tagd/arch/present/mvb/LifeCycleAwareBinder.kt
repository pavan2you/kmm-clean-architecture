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

package io.tagd.arch.present.mvb

import io.tagd.arch.datatype.DataObjectable
import io.tagd.langx.ref.WeakReference

abstract class LifeCycleAwareBinder<T: DataObjectable, V: BindableView<T>>(view: V) : Binder<T, V> {

    private val viewReference = WeakReference(view)

    override val view: V?
        get() = viewReference.get()

    override fun onCreate() {
        //Optional
    }

    override fun onUnbind() {
        //Optional
    }

    override fun onDestroy() {
        release()
    }

    override fun release() {
        viewReference.clear()
    }
}