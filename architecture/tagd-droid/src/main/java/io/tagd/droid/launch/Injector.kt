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

import io.tagd.arch.infra.InfraService
import io.tagd.di.Global
import io.tagd.di.layer
import io.tagd.droid.lifecycle.ReadyLifeCycleEventDispatcher
import java.lang.ref.WeakReference

open class Injector(application: TagdApplication) : AppService {

    protected var appReference: WeakReference<TagdApplication>? = WeakReference(application)

    protected val app: TagdApplication?
        get() = appReference?.get()

    open fun inject() {
        val application = app!!
        with(Global) {
            injectAppServicesLayer(application)
        }
    }

    protected open fun Global.injectAppServicesLayer(application: TagdApplication) {
        layer<InfraService> {
            bind<AppForegroundBackgroundSenser>().toInstance(
                AppForegroundBackgroundSenser(application)
            )
            bind<ReadyLifeCycleEventDispatcher>().toInstance(
                ReadyLifeCycleEventDispatcher()
            )
        }
    }

    override fun release() {
        appReference?.clear()
        appReference = null
    }

    companion object {
        fun setInjector(injector: Injector) {
            with(Global) {
                layer<InfraService> {
                    bind<Injector>().toInstance(injector)
                }
            }
        }
    }
}


