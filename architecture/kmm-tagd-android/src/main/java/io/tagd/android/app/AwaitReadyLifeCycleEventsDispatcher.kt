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

import io.tagd.arch.app.AppService
import java.lang.ref.WeakReference
import java.util.*

//todo - ExtendedLifeCycleEventDispatcher
class AwaitReadyLifeCycleEventsDispatcher : AppService {

    private var queue : Queue<WeakReference<AwaitReadyLifeCycleStatesOwner>>? = ArrayDeque()
    private var ready: Boolean = false

    fun register(owner: AwaitReadyLifeCycleStatesOwner) {
        queue?.offer(WeakReference(owner))
    }

    fun unregister(owner: AwaitReadyLifeCycleStatesOwner) {
        queue?.firstOrNull {
            it?.get() === owner
        }?.let {
            queue?.remove(it)
        }
    }

    fun dispatchReady() {
        ready = true
        dispatchOnInject()
        dispatchOnReady()
        clearRegistry()
    }

    private fun dispatchOnInject() {
        queue?.forEach { weakOwner ->
            weakOwner?.get()?.let { owner ->
                if (owner.needInjection()) {
                    owner.onInject()
                }
            }
        }
    }

    private fun dispatchOnReady() {
        queue?.forEach {
            it?.get()?.onReady()
        }
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