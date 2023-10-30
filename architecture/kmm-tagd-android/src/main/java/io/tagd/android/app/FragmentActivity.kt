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

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class FragmentActivity : androidx.fragment.app.FragmentActivity(),
    AwaitReadyLifeCycleStatesOwner {

    private var lifecycleFreeState: HeadLessFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleFreeState = getOrNewLifecycleFreeState()
        interceptOnCreate(savedInstanceState)
        onCreateView(savedInstanceState)
    }

    private fun getOrNewLifecycleFreeState() =
        supportFragmentManager.findFragmentByTag(HeadLessFragment.TAG) as? HeadLessFragment
            ?: HeadLessFragment.new(this)

    protected open fun interceptOnCreate(savedInstanceState: Bundle?) {
        // no-op
    }

    protected abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun onStart() {
        super.onStart()
        interceptOnStart()
        if (awaitReadyLifeCycleEventsDispatcher().ready()) {
            if (needInjection()) {
                onInject()
            }
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

    override fun needInjection(): Boolean {
        return lifecycleFreeState?.triggerInject ?: true
    }

    override fun onInject() {
        assert(lifecycleFreeState?.triggerInject ?: true)
        lifecycleFreeState?.triggerInject = false
    }

    override fun onReady() {
        // no-op
    }

    override fun onDestroy() {
        awaitReadyLifeCycleEventsDispatcher().unregister(this)
        lifecycleFreeState = null
        super.onDestroy()
    }

    override fun onRestart() {
        if (!suppressInjectionOnRestart()) {
            lifecycleFreeState?.triggerInject = true
        }
        super.onRestart()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        lifecycleFreeState?.triggerInject = true
        super.onConfigurationChanged(newConfig)
    }

    protected open fun suppressInjectionOnRestart(): Boolean {
        return true
    }
}

class HeadLessFragment : Fragment() {

    var triggerInject: Boolean = true

    init {
        retainInstance = true //since this activity is not just for ViewModels then deprecation doesn't make sense
    }

    companion object {
        const val TAG = "head-less"

        @JvmStatic
        fun new(activity: FragmentActivity): HeadLessFragment {
            val fragment = HeadLessFragment()
            activity.supportFragmentManager
                .beginTransaction()
                .add(fragment, TAG)
                .commitNow()

            return fragment
        }
    }
}
