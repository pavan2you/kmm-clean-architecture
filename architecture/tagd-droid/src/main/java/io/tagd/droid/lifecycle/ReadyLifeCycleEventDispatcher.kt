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

package io.tagd.droid.lifecycle

import io.tagd.droid.launch.AppService
import java.lang.ref.WeakReference
import java.util.*

class ReadyLifeCycleEventDispatcher : AppService {

    private var queue : Queue<WeakReference<ReadyLifeCycleEventOwner>>? = ArrayDeque()
    private var ready: Boolean = false

    fun register(owner: ReadyLifeCycleEventOwner) {
        queue?.offer(WeakReference(owner))
    }

    fun unregister(owner: ReadyLifeCycleEventOwner) {
        queue?.firstOrNull {
            it?.get() === owner
        }?.let {
            queue?.remove(it)
        }
    }

    fun dispatchOnReady() {
        ready = true
        queue?.forEach {
            it?.get()?.onReady()
        }
        clearRegistry()
    }

    fun ready() : Boolean {
        return ready
    }

    private fun clearRegistry() {
        queue?.forEach {
            it?.clear()
        }
        queue?.clear()
    }

    override fun release() {
        clearRegistry()
        queue = null
    }
}