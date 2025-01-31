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

import io.tagd.android.crosscutting.async.IgnoredCoroutineException
import io.tagd.arch.app.AppService
import io.tagd.core.AsyncExceptionHandler
import java.lang.ref.WeakReference

open class AppUncaughtExceptionHandler(
    app: TagdApplication,
    defaultHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler, AppService, AsyncExceptionHandler {

    private var weakApplication: WeakReference<TagdApplication>? = WeakReference(app)
    private var weakDefaultHandler: WeakReference<Thread.UncaughtExceptionHandler>? =
        WeakReference(defaultHandler)

    override fun uncaughtException(t: Thread, e: Throwable) {
        weakDefaultHandler?.get()?.uncaughtException(t, e)
    }

    override fun asyncException(throwable: Throwable) {
        if (throwable !is IgnoredCoroutineException) {
            uncaughtException(Thread.currentThread(), throwable)
        }
    }

    override fun release() {
        weakApplication?.clear()
        weakApplication = null

        weakDefaultHandler?.clear()
        weakDefaultHandler = null
    }
}