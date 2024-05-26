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

import io.tagd.arch.infra.InfraService
import io.tagd.arch.scopable.WithinScopableInjectionChain
import io.tagd.di.layer

interface ApplicationInjector<A : IApplication> : AppService, WithinScopableInjectionChain<A> {

    companion object {

        fun <A : IApplication> setInjector(injector: ApplicationInjector<A>) {
            with(injector.within.thisScope) {
                layer<InfraService> {
                    bind<ApplicationInjector<A>>().toInstance(injector)
                }
            }
        }
    }
}


