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

package io.tagd.arch.app

import io.tagd.langx.collection.WeakArrayList
import io.tagd.langx.ref.WeakReference

open class LifeCycleAwareApplicationController<A : IApplication>(application: A) :
    ApplicationController<A> {

    private var applicationReference: WeakReference<A>? = WeakReference(application)

    override val app: A?
        get() = applicationReference?.get()

    private var lifeCycleListeners: WeakArrayList<ApplicationLifeCycleListener> = WeakArrayList()

    fun addLifeCycleListener(listener: ApplicationLifeCycleListener) {
        lifeCycleListeners.add(listener)
    }

    fun removeLifeCycleListener(listener: ApplicationLifeCycleListener) {
        lifeCycleListeners.remove(listener)
    }

    override fun onCreate() {
        //no op
    }

    override fun onLaunch() {
        //no op
    }

    override fun onUpgrade(oldVersion: Int, currentVersion: Int) {
        //no op
    }

    override fun onLoading() {
        //no op
    }

    override fun onReady() {
        //no op
    }

    override fun onForeground() {
        app?.let { application ->
            lifeCycleListeners.forEach { listener ->
                listener.onForeground(application)
            }
        }
    }

    override fun onBackground() {
        app?.let { application ->
            lifeCycleListeners.forEach { listener ->
                listener.onBackground(application)
            }
        }
    }

    override fun onDestroy() {
        release()
    }

    override fun release() {
        lifeCycleListeners.clear()
        applicationReference = null
    }
}