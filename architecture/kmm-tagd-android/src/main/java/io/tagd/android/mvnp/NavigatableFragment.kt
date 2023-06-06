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

package io.tagd.android.mvnp

import android.os.Bundle
import io.tagd.android.mvp.MvpFragment
import io.tagd.arch.present.mvnp.NavigatablePresenter
import io.tagd.arch.present.mvnp.NavigatableView
import io.tagd.arch.present.mvnp.Navigator

abstract class NavigatableFragment<
    V : NavigatableView,
    N : Navigator<V>,
    P : NavigatablePresenter<V, N>
> : MvpFragment<V, P>(), NavigatableView {

    protected var navigator: N? = null

    override fun interceptOnCreate(savedInstanceState: Bundle?) {
        navigator = onCreateNavigator(savedInstanceState)
        super.interceptOnCreate(savedInstanceState)
    }

    protected abstract fun onCreateNavigator(savedInstanceState: Bundle?): N

    override fun navigator(): Navigator<V>? = navigator

    override fun release() {
        navigator?.release()
        navigator = null
        super.release()
    }
}