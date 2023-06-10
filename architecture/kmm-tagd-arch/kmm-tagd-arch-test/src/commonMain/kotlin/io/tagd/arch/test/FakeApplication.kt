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

package io.tagd.arch.test

import io.tagd.arch.control.ApplicationController
import io.tagd.arch.control.IApplication
import io.tagd.arch.control.LifeCycleAwareApplicationController
import io.tagd.arch.control.VersionTracker
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.di.Scope

class FakeApplication : IApplication {

    override val name: String
        get() = "fake"
    override val scope: Scope
        get() = Scope("fake")

    var controller: ApplicationController<IApplication>? =
        LifeCycleAwareApplicationController(this)

    override fun versionTracker(): VersionTracker {
        return VersionTracker(1, 1)
    }

    override fun currentView(): PresentableView? {
        return null
    }

    override fun previousView(): PresentableView? {
        return null
    }

    override fun controller(): ApplicationController<*>? {
        return controller
    }

    override fun release() {
        controller?.onDestroy()
        controller = null
    }
}