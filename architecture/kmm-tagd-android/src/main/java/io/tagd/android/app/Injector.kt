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

import io.tagd.android.crosscutting.async.CoroutineCacheIOStrategy
import io.tagd.android.crosscutting.async.CoroutineComputationStrategy
import io.tagd.android.crosscutting.async.CoroutineDaoIOStrategy
import io.tagd.android.crosscutting.async.CoroutineDiskStrategy
import io.tagd.android.crosscutting.async.CoroutineNetworkStrategy
import io.tagd.android.crosscutting.async.CoroutinePresentationStrategy
import io.tagd.android.crosscutting.codec.GsonJsonCodec
import io.tagd.arch.access.bind
import io.tagd.arch.control.IApplication
import io.tagd.arch.domain.crosscutting.CrossCutting
import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.arch.domain.crosscutting.async.CacheIOStrategy
import io.tagd.arch.domain.crosscutting.async.ComputationStrategy
import io.tagd.arch.domain.crosscutting.async.DaoIOStrategy
import io.tagd.arch.domain.crosscutting.async.DiskIOStrategy
import io.tagd.arch.domain.crosscutting.async.NetworkIOStrategy
import io.tagd.arch.domain.crosscutting.async.PresentationStrategy
import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.arch.domain.crosscutting.async.present
import io.tagd.arch.domain.crosscutting.codec.JsonCodec
import io.tagd.arch.infra.InfraService
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.di.Global
import io.tagd.di.key2
import io.tagd.di.layer
import io.tagd.kotlinx.coroutines.Computation
import io.tagd.kotlinx.coroutines.DaoIO
import io.tagd.kotlinx.coroutines.Dispatchers
import java.lang.ref.WeakReference

open class Injector(application: TagdApplication) : AppService, AsyncContext {

    protected var appReference: WeakReference<TagdApplication>? = WeakReference(application)

    protected val app: TagdApplication?
        get() = appReference?.get()

    /**
     * Must be used for only synchronous injection, and must not be called this beyond application
     * setup flow
     */
    open fun setup() {
        //no-op
    }

    open fun inject() {
        injectSynchronously()
        injectAsynchronously()
    }

    protected open fun injectSynchronously() {
        app?.let { application ->
            with(Global) {
                injectInfraLayer(application)
            }
        }
    }

    protected open fun Global.injectInfraLayer(application: TagdApplication) {
        injectAppServicesLayer(application)
        injectAppReferencesLayer(application)
        injectCrossCuttings()
    }

    protected open fun Global.injectAppServicesLayer(application: TagdApplication) {
        layer {
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
            bind(
                key2<ReferenceHolder<
                        WeakReference<IApplication>>,
                        WeakReference<IApplication>>(),
                ReferenceHolder(WeakReference(application))
            )
        }
    }

    private fun provideDispatchers(): Dispatchers {
        Dispatchers.set(
            Dispatchers.Builder()
                .Main(kotlinx.coroutines.Dispatchers.Main.immediate)
                .Computation(kotlinx.coroutines.Dispatchers.Computation)
                .IO(kotlinx.coroutines.Dispatchers.IO)
                .DaoIO(kotlinx.coroutines.Dispatchers.DaoIO)
                .Unconfined(kotlinx.coroutines.Dispatchers.Unconfined)
                .build()
        )
        return Dispatchers.get()
    }

    private fun Global.injectCrossCuttings() {
        layer<CrossCutting> {
            bind<PresentationStrategy>().toInstance(CoroutinePresentationStrategy())
            bind<ComputationStrategy>().toInstance(CoroutineComputationStrategy())
            bind<NetworkIOStrategy>().toInstance(CoroutineNetworkStrategy())
            bind<DiskIOStrategy>().toInstance(CoroutineDiskStrategy())
            bind<DaoIOStrategy>().toInstance(CoroutineDaoIOStrategy())
            bind<CacheIOStrategy>().toInstance(CoroutineCacheIOStrategy())

            bind<CrossCutting, JsonCodec>(instance = GsonJsonCodec())
        }
    }

    protected open fun injectAsynchronously() {
        //no-op
    }

    protected open fun dispatchDone() {
        present {
            app?.dispatchLoadingStepDone(AppLoadingStateHandler.Steps.INJECTING)
        }
    }

    override fun release() {
        cancelAsync()
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


