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

package io.tagd.arch.present.mvp

import io.tagd.core.annotation.Visibility
import io.tagd.core.annotation.VisibleForTesting
import io.tagd.langx.assert
import io.tagd.langx.ref.WeakReference

open class LifeCycleAwarePresenter<V : PresentableView>(view: V) : Presenter<V> {

    private var weakView: WeakReference<V>? = WeakReference(view)

    @VisibleForTesting(otherwise = Visibility.PROTECTED)
    var canHandleBackPress: Boolean? = true
        set(value) {
            field = value

            value?.let {
                view?.enableBackPressCallback(value)
            }
        }

    override val view: V?
        get() = weakView?.get()

    init {
        attach(view)
    }

    override fun attach(view: V) {
        weakView = WeakReference(view)
    }

    override fun detach(view: V) {
        assert(weakView?.get() === view)
        weakView?.clear()
    }

    override fun onCreate() {
        //no op
    }

    override fun onStart() {
        //no op
    }

    override fun onResume() {
        //no op
    }

    override fun onAwaiting() {
        //no op
    }

    override fun onInject() {
        //no op
    }

    override fun onReady() {
        //no op
    }

    override fun onPause() {
        //no op
    }

    override fun onStop() {
        //no op
    }

    override fun onDestroy() {
        release()
    }

    override fun onBackPressed() {
        //no op
    }

    override fun canHandleBackPress(): Boolean {
        return canHandleBackPress ?: false
    }

    override fun release() {
        weakView?.clear()
        weakView = null
        canHandleBackPress = null
    }
}