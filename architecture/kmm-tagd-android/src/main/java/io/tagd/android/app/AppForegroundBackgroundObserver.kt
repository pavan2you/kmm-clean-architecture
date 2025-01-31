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

import android.os.Handler
import android.os.Looper
import io.tagd.arch.app.AppService
import java.lang.ref.WeakReference

private const val DEFAULT_BG_TIME = 2000L

open class AppForegroundBackgroundObserver(
    application: TagdApplication,
    private val backgroundTimeMs: Long = DEFAULT_BG_TIME
) : AppService {

    private var appReference: WeakReference<TagdApplication>? = WeakReference(application)
    protected val app: TagdApplication?
        get() = appReference?.get()

    private var handler: Handler? = Handler(Looper.getMainLooper())
    private var watcher: Runnable? = Runnable {
        handleAppWentToBackground()
    }

    private var backgroundSince: Long = 0

    fun dispatchActivityStart() {
        handleAppCameToForeground()
    }

    fun dispatchActivityStop() {
        backgroundSince = System.currentTimeMillis()
        watchBackground()
    }

    fun dispatchActivityDestroyed(isLast: Boolean = false) {
        determineAndDispatchAppExit(isLast)
    }

    private fun watchBackground() {
        watcher?.let {
            handler?.postDelayed(it, backgroundTimeMs)
        }
    }

    private fun handleAppCameToForeground() {
        val wasInBackground = backgroundSince >= 0L && System.currentTimeMillis() > backgroundSince
        if (wasInBackground) {
            val timeSpentInBackground = System.currentTimeMillis() - backgroundSince

            if (timeSpentInBackground > backgroundTimeMs) {
                app?.onForeground()
            }
            removeWatcher()
            backgroundSince = -1L
        }
    }

    private fun handleAppWentToBackground() {
        if (System.currentTimeMillis() - backgroundSince > backgroundTimeMs) {
            app?.onBackground()
        }
        removeWatcher()
    }

    private fun determineAndDispatchAppExit(isLast: Boolean) {
        if (isLast) {
            removeWatcher()
            app?.onExit()
        }
    }

    private fun removeWatcher() {
        watcher?.let { handler?.removeCallbacks(it) }
    }

    override fun release() {
        appReference?.clear()
        appReference = null
    }
}
