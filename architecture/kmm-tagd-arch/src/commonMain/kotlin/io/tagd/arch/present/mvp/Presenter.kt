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

import io.tagd.core.AsyncContext

interface Presenter<V : PresentableView> : AsyncContext {

    val view: V?

    fun attach(view: V)

    fun detach(view: V)

    fun onCreate()

    fun onStart()

    fun onResume()

    fun onAwaiting()

    fun onInject()

    fun onReady()

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun onBackPressed()

    fun canHandleBackPress(): Boolean

}
