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

package io.tagd.android.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

open class ActivityLifeCycleObserver(application: TagdApplication) :
    Application.ActivityLifecycleCallbacks, ComponentLifeCycleObserver<Activity> {

    private var awaitingToLaunch: Boolean = true

    private var appReference: WeakReference<TagdApplication>? = WeakReference(application)
    protected val app: TagdApplication?
        get() = appReference?.get()

    private var trace = arrayListOf<WeakReference<Activity>>()

    private var isFirst = true

    private val current: WeakReference<Activity>?
        get() = trace.lastOrNull()

    private val previous: WeakReference<Activity>?
        get() = if (trace.size > 1) {
            trace[trace.size - 2]
        } else {
            null
        }

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
        awaitingToLaunch = false
        setupAppLauncher(activity, savedInstanceState)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (awaitingToLaunch) {
            awaitingToLaunch = false
            setupAppLauncher(activity, savedInstanceState)
        }
        setPreviousAndCurrent(activity)
    }

    private fun setupAppLauncher(activity: Activity, savedInstanceState: Bundle?) {
        if (isFirst) {
            isFirst = false
            app?.resolveLauncher(activity, savedInstanceState)
        }
    }

    private fun setPreviousAndCurrent(activity: Activity) {
        trace.add(WeakReference(activity))
    }

    override fun onActivityStarted(activity: Activity) {
        if (current?.get() != activity) {
            setPreviousAndCurrent(activity)
        }
        app?.appService<AppForegroundBackgroundObserver>()?.dispatchActivityStart()
    }

    override fun onActivityResumed(activity: Activity) {
        if (current?.get() === activity) {
            return
        }
        setPreviousAndCurrent(activity)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
        if (current?.get() === activity) {
            app?.appService<AppForegroundBackgroundObserver>()?.dispatchActivityStop()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        //no op
    }

    override fun onActivityDestroyed(activity: Activity) {
        val isLast = isLast(activity)
        nullifyIfCurrentOrPrevious(activity)
        app?.appService<AppForegroundBackgroundObserver>()?.dispatchActivityDestroyed(isLast)
    }

    private fun nullifyIfCurrentOrPrevious(activity: Activity) {
        if (current?.get() === activity) {
            current?.clear()
            trace.removeAt(trace.size - 1)
        } else {
            if (previous?.get() === activity) {
                previous?.clear()
                if (trace.size > 1) {
                    trace.removeAt(trace.size - 2)
                }
            }
        }
    }

    private fun isLast(activity: Activity): Boolean {
        return trace.size == 1 && (current?.get() === activity)
    }

    fun currentActivity(): Activity? = current?.get()

    fun previousActivity(): Activity? = previous?.get()

    override fun release() {
        appReference?.clear()
        previous?.clear()
        current?.clear()
        trace.clear()
        appReference = null
    }
}