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
import io.tagd.android.app.Fragment
import io.tagd.android.app.TagdApplication
import io.tagd.arch.control.IApplication
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.arch.present.mvp.Presenter

abstract class MvpFragment<V : PresentableView, P : Presenter<V>> : Fragment(), PresentableView {

    protected open var presenter: P? = null

    override val app: IApplication?
        get() = context?.applicationContext as? IApplication

    override fun interceptOnCreate(savedInstanceState: Bundle?) {
        super.interceptOnCreate(savedInstanceState)

        (context?.applicationContext as? TagdApplication)?.presenterFactory()
            ?.let { presenterFactory ->
                presenter = presenterFactory.getOrNew(this::class) {
                    presenter = onCreatePresenter(savedInstanceState)
                    presenter!!
                } as P
                presenter!!.attach(this as V)
            } ?: kotlin.run {
            presenter = onCreatePresenter(savedInstanceState)
        }

        presenter?.onCreate()
    }

    protected abstract fun onCreatePresenter(savedInstanceState: Bundle?): P?

    @Suppress("UNCHECKED_CAST")
    override fun <V : PresentableView> presenter(): Presenter<V>? = presenter as? Presenter<V>

    override fun interceptOnStart() {
        super.interceptOnStart()
        presenter?.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onAwaiting() {
        super.onAwaiting()
        presenter?.onAwaiting()
    }

    override fun onReady() {
        super.onReady()
        presenter?.onReady()
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
        (context?.applicationContext as? TagdApplication)?.presenterFactory()
            ?.let { presenterFactory ->
                presenterFactory.get(this::class)?.detach(this)
            } ?: kotlin.run {
            presenter?.onDestroy()
        }

        release()
        super.onDestroy()
    }

    open fun onBackPressed() {
        if (presenter != null && presenter?.canHandleBackPress() == true) {
            presenter?.onBackPressed()
        }
    }

    override fun release() {
        awaitReadyLifeCycleEventsDispatcher().unregister(this)
        presenter = null
    }
}