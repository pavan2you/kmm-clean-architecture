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

package io.tagd.arch.control

import io.tagd.langx.ref.WeakReference

class LifeCycleAwareApplicationController(application: IApplication) :
    ApplicationController<IApplication> {

    private var applicationReference: WeakReference<IApplication>? = WeakReference(application)

    override val app: IApplication?
        get() = applicationReference?.get()

    override fun onCreate() {
        //no op
    }

    override fun onLaunch() {
        //no op
    }

    override fun onLoading() {
        //no op
    }

    override fun onReady() {
        //no op
    }

    override fun onForeground() {
        //no op
    }

    override fun onBackground() {
        //no op
    }

    override fun onDestroy() {
        release()
    }

    override fun release() {
        applicationReference = null
    }
}