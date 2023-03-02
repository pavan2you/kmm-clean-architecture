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

    private var appReference: WeakReference<TagdApplication>? = WeakReference(application)
    protected val app: TagdApplication?
        get() = appReference?.get()

    private var previous: WeakReference<Activity>? = null
    private var current: WeakReference<Activity>? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        setPreviousAndCurrent(activity)
        setupAppLauncher(activity, savedInstanceState)
    }

    private fun setupAppLauncher(activity: Activity, savedInstanceState: Bundle?) {
        if (isFirst(activity)) {
            app?.resolveLauncher(activity, savedInstanceState)
        }
    }

    private fun isFirst(activity: Activity): Boolean {
        return (previous == null || current != null && current?.get() === activity)
    }

    private fun setPreviousAndCurrent(activity: Activity) {
        previous = current
        current = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        if (current?.get() === activity) {
            return
        }
        setPreviousAndCurrent(activity)
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
        app?.appService<AppForegroundBackgroundObserver>()?.dispatchActivityStop()
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
            current = null
        } else {
            if (previous?.get() === activity) {
                previous?.clear()
                previous = null
            }
        }
    }

    private fun isLast(activity: Activity): Boolean {
        return (current?.get() === activity) && (previous == null || previous?.get() == null)
    }

    fun currentActivity(): Activity? = current?.get()

    fun previousActivity(): Activity? = previous?.get()

    override fun release() {
        appReference?.clear()
        previous?.clear()
        current?.clear()
        appReference = null
        previous = null
        current = null
    }
}