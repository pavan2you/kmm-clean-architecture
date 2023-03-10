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

package io.tagd.android.mvp

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.tagd.arch.control.IApplication
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.arch.present.mvp.Presenter
import io.tagd.android.app.TagdApplication
import io.tagd.android.app.AwaitReadyLifeCycleEventsDispatcher
import io.tagd.android.app.AwaitReadyLifeCycleStatesOwner

abstract class MvpFragment<V : PresentableView, P : Presenter<V>> : Fragment(), PresentableView,
    AwaitReadyLifeCycleStatesOwner {

    protected var presenter: P? = null

    override val app: IApplication?
        get() = context?.applicationContext as? IApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = onCreatePresenter(savedInstanceState)
        presenter?.onCreate()
        onCreateView(savedInstanceState)
    }

    protected abstract fun onCreatePresenter(savedInstanceState: Bundle?): P?

    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun <V : PresentableView> presenter(): Presenter<V>? = presenter as? Presenter<V>

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
        if (awaitReadyLifeCycleEventsDispatcher().ready()) {
            onReady()
        } else {
            awaitReadyLifeCycleEventsDispatcher().register(this)
            onAwaiting()
        }
    }

    override fun awaitReadyLifeCycleEventsDispatcher(): AwaitReadyLifeCycleEventsDispatcher {
        return (context?.applicationContext as TagdApplication).appService()!!
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onPause() {
        presenter?.onPause()
        super.onPause()
    }

    override fun onStop() {
        presenter?.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        presenter?.onDestroy()
        release()
        super.onDestroy()
    }

    fun onBackPressed() {
        if (presenter != null && presenter?.canHandleBackPress() == true) {
            presenter?.onBackPressed()
        }
    }

    override fun release() {
        awaitReadyLifeCycleEventsDispatcher().unregister(this)
        presenter = null
    }
}