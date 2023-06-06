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

import android.os.Bundle

abstract class ComponentActivity :
    androidx.activity.ComponentActivity(),
    AwaitReadyLifeCycleStatesOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        interceptOnCreate(savedInstanceState)
        onCreateView(savedInstanceState)
    }

    protected open fun interceptOnCreate(savedInstanceState: Bundle?) {
        // no-op
    }

    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun onStart() {
        super.onStart()
        interceptOnStart()
        if (awaitReadyLifeCycleEventsDispatcher().ready()) {
            onReady()
        } else {
            awaitReadyLifeCycleEventsDispatcher().register(this)
            onAwaiting()
        }
    }

    protected open fun interceptOnStart() {
        // no-op
    }

    override fun awaitReadyLifeCycleEventsDispatcher(): AwaitReadyLifeCycleEventsDispatcher {
        return (application as TagdApplication).appService()!!
    }

    override fun onAwaiting() {
        // no-op
    }

    override fun onReady() {
        // no-op
    }

    override fun onDestroy() {
        awaitReadyLifeCycleEventsDispatcher().unregister(this)
        super.onDestroy()
    }
}