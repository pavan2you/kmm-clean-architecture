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

package io.tagd.the101.android

import io.tagd.android.app.TagdApplication
import io.tagd.android.app.loadingstate.AppLoadingStateHandler
import io.tagd.android.app.loadingstate.AppLoadingStepDispatcher
import io.tagd.arch.scopable.AbstractWithinScopableInjector

class SampleApplication : TagdApplication() {

    override fun newLoadingStateHandler(
        dispatcher: AppLoadingStepDispatcher<out TagdApplication>
    ): AppLoadingStateHandler {

        return SampleAppLoadingStateHandler(dispatcher)
    }

    override fun newInjector(): SampleAppInjector {
        return SampleAppInjector(this)
    }
}