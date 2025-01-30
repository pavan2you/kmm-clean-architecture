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
import io.tagd.android.crosscutting.async.CoroutineComputeIOStrategy
import io.tagd.android.crosscutting.async.CoroutineDaoIOStrategy
import io.tagd.android.crosscutting.async.CoroutineDiskStrategy
import io.tagd.android.crosscutting.async.CoroutineNetworkStrategy
import io.tagd.android.crosscutting.async.CoroutinePresentationStrategy
import io.tagd.android.crosscutting.codec.GsonJsonCodec
import io.tagd.android.crosscutting.codec.UrlEncoderDecoder
import io.tagd.arch.control.ApplicationInjector
import io.tagd.arch.control.IApplication
import io.tagd.arch.control.LoadingStateHandler
import io.tagd.arch.domain.crosscutting.async.CacheIOStrategy
import io.tagd.arch.domain.crosscutting.async.ComputationStrategy
import io.tagd.arch.domain.crosscutting.async.ComputeIOStrategy
import io.tagd.arch.domain.crosscutting.async.DaoIOStrategy
import io.tagd.arch.domain.crosscutting.async.DiskIOStrategy
import io.tagd.arch.domain.crosscutting.async.NetworkIOStrategy
import io.tagd.arch.domain.crosscutting.async.PresentationStrategy
import io.tagd.arch.domain.crosscutting.codec.JsonCodec
import io.tagd.arch.domain.crosscutting.codec.UrlCodec
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.arch.scopable.AbstractWithinScopableInjector
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.WithinScopableInitializer
import io.tagd.core.AsyncStrategy
import io.tagd.core.CrossCutting
import io.tagd.di.Scope
import io.tagd.di.bind
import io.tagd.di.key2
import io.tagd.di.layer
import io.tagd.kotlinx.coroutines.Computation
import io.tagd.kotlinx.coroutines.ComputeIO
import io.tagd.kotlinx.coroutines.DaoIO
import io.tagd.kotlinx.coroutines.Dispatchers
import java.lang.ref.WeakReference

open class TagdApplicationInjector<T : TagdApplication>(
    application: T
) : AbstractWithinScopableInjector<T>(within = application), ApplicationInjector<T> {

    /**
     * Must be used for only synchronous injection, and must not be called this beyond application
     * setup flow
     */
    override fun setup() {
        with(within.thisScope) {
            injectInfraLayer(within)
        }
    }

    protected open fun Scope.injectInfraLayer(application: TagdApplication) {
        injectAppServicesLayer(application)
        injectAppReferencesLayer(application)
        injectCrossCuttings()
    }

    protected open fun Scope.injectAppServicesLayer(application: TagdApplication) {
        layer {
            bind<AppForegroundBackgroundObserver>().toInstance(
                AppForegroundBackgroundObserver(application)
            )
            bind<AwaitReadyLifeCycleEventsDispatcher>().toInstance(
                AwaitReadyLifeCycleEventsDispatcher()
            )
        }
    }

    protected open fun Scope.injectAppReferencesLayer(application: TagdApplication) {
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
                .ComputeIO(kotlinx.coroutines.Dispatchers.ComputeIO)
                .DaoIO(kotlinx.coroutines.Dispatchers.DaoIO)
                .Unconfined(kotlinx.coroutines.Dispatchers.Unconfined)
                .build()
        )
        return Dispatchers.get()
    }

    private fun Scope.injectCrossCuttings() {
        System.setProperty("kotlinx.coroutines.debug", "on" )

        layer<CrossCutting> {
            bind<PresentationStrategy>().toInstance(CoroutinePresentationStrategy())
            bind<ComputationStrategy>().toInstance(CoroutineComputationStrategy().also {
                AsyncStrategy.serial = it
            })
            bind<ComputeIOStrategy>().toInstance(CoroutineComputeIOStrategy().also {
                AsyncStrategy.concurrent = it
            })
            bind<NetworkIOStrategy>().toInstance(CoroutineNetworkStrategy())
            bind<DiskIOStrategy>().toInstance(CoroutineDiskStrategy())
            bind<DaoIOStrategy>().toInstance(CoroutineDaoIOStrategy())
            bind<CacheIOStrategy>().toInstance(CoroutineCacheIOStrategy())

            this@injectCrossCuttings.bind<CrossCutting, JsonCodec<*>>(
                instance = GsonJsonCodec.new()
            )
            this@injectCrossCuttings.bind<CrossCutting, UrlCodec>(instance = UrlEncoderDecoder())
        }
    }

    override fun load(initializers: ArrayList<WithinScopableInitializer<T, *>>) {
        //no-op
    }

    override fun <WITHIN : Scopable> canRegisterLoadingSteps(
        handler: LoadingStateHandler<WITHIN, *, *>
    ): Boolean {

        return handler.scopable === within
    }

    override fun release() {
        AsyncStrategy.release();
        super.release()
    }
}