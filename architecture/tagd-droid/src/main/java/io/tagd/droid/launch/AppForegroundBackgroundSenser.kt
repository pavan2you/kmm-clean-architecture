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

package io.tagd.droid.launch

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference

private const val DEFAULT_BG_TIME = 2000L

open class AppForegroundBackgroundSenser(application: TagdApplication) : AppService {

    private var appReference: WeakReference<TagdApplication>? = WeakReference(application)
    protected val app: TagdApplication?
        get() = appReference?.get()

    private var handler: Handler? = Handler(Looper.getMainLooper())
    private var watcher: Runnable? = Runnable {
        senseAppWentToBackground()
    }

    private var backgroundSince: Long = 0

    fun dispatchActivityStart() {
        senseAppCameToForeground()
    }

    fun dispatchActivityStop() {
        backgroundSince = System.currentTimeMillis()
        watchBackground()
    }

    fun dispatchActivityDestroyed(isLast: Boolean = false) {
        senseAppExit(isLast)
    }

    private fun watchBackground() {
        watcher?.let {
            handler?.postDelayed(it, DEFAULT_BG_TIME)
        }
    }

    private fun senseAppCameToForeground() {
        if (backgroundSince == 0L ||
            (backgroundSince > 0L && System.currentTimeMillis() - backgroundSince > DEFAULT_BG_TIME)
        ) {

            removeWatcher()
            backgroundSince = -1L
            app?.onForeground()
        }
    }

    private fun senseAppWentToBackground() {
        if (System.currentTimeMillis() - backgroundSince > DEFAULT_BG_TIME) {
            app?.onBackground()
        }
        removeWatcher()
    }

    private fun senseAppExit(isLast: Boolean) {
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
