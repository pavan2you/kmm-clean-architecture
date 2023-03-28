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

import io.tagd.arch.infra.InfraService
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.di.Global
import io.tagd.di.key2
import io.tagd.di.layer
import io.tagd.kotlinx.coroutines.Dispatchers
import java.lang.ref.WeakReference

open class Injector(application: TagdApplication) : AppService {

    protected var appReference: WeakReference<TagdApplication>? = WeakReference(application)

    protected val app: TagdApplication?
        get() = appReference?.get()

    open fun inject() {
        app?.let { application ->
            with(Global) {
                injectInfraLayer(application)
            }
        }
    }

    protected open fun Global.injectInfraLayer(application: TagdApplication) {
        injectAppServicesLayer(application)
        injectAppReferencesLayer(application)
    }

    protected open fun Global.injectAppServicesLayer(application: TagdApplication) {
        layer<InfraService> {
            bind<AppForegroundBackgroundObserver>().toInstance(
                AppForegroundBackgroundObserver(application)
            )
            bind<AwaitReadyLifeCycleEventsDispatcher>().toInstance(
                AwaitReadyLifeCycleEventsDispatcher()
            )
        }
    }

    protected open fun Global.injectAppReferencesLayer(application: TagdApplication) {
        layer<ReferenceHolder<*>> {
            bind(
                key2<ReferenceHolder<Dispatchers>, Dispatchers>(),
                ReferenceHolder(provideDispatchers())
            )
        }
    }

    private fun provideDispatchers(): Dispatchers {
        Dispatchers.set(
            Dispatchers.Builder()
                .Main(kotlinx.coroutines.Dispatchers.Main.immediate)
                .Default(kotlinx.coroutines.Dispatchers.Default)
                .IO(kotlinx.coroutines.Dispatchers.IO)
                .Unconfined(kotlinx.coroutines.Dispatchers.Unconfined)
                .build()
        )
        return Dispatchers.get()
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

inline fun <reified T : Any> Injector.bindReference(reference: T) {
    Global.layer<ReferenceHolder<*>> {
        bind(key2<ReferenceHolder<T>, T>()).toInstance(ReferenceHolder(reference))
    }
}


