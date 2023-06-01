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

package io.tagd.arch.control

import io.tagd.arch.access.reference
import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.arch.module.Module
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.di.key2
import io.tagd.langx.ref.WeakReference

interface IApplication : Module, AsyncContext {

    fun versionTracker() : VersionTracker

    fun currentView(): PresentableView?

    fun previousView(): PresentableView?

    fun controller(): ApplicationController<*>?
}

fun application(): IApplication? {
    return reference(key2<ReferenceHolder<
            WeakReference<IApplication>>,
            WeakReference<IApplication>>())?.get()
}