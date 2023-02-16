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

package io.tagd.arch.fake

import io.tagd.arch.domain.crosscutting.CrossCutting
import io.tagd.arch.domain.crosscutting.async.*
import io.tagd.core.Releasable
import io.tagd.di.Global
import io.tagd.di.layer

class FakeInjector : Releasable {

    fun inject() {
        with(Global) {
            injectCrossCuttings()
        }
    }

    private fun Global.injectCrossCuttings() {
        // global - cross cuttings
        layer<CrossCutting> {

            //platform
            val testStrategy = FakeAsyncStrategy()
            bind<ComputationStrategy>().toInstance(testStrategy)
            bind<PresentationStrategy>().toInstance(testStrategy)
            bind<NetworkIOStrategy>().toInstance(testStrategy)
            bind<DiskIOStrategy>().toInstance(testStrategy)
            bind<DaoStrategy>().toInstance(testStrategy)
        }
    }

    override fun release() {
        Global.reset()
    }

    companion object {

        private var active: FakeInjector? = null

        fun inject(): FakeInjector {
            release()
            active = FakeInjector().apply { inject() }
            return active!!
        }

        fun release() {
            active?.release()
        }
    }
}