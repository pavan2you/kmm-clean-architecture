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

package io.tagd.arch.access

import com.nhaarman.mockito_kotlin.mock
import io.tagd.arch.data.cache.Cache
import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.arch.domain.crosscutting.CrossCutting
import io.tagd.arch.domain.service.DomainService
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.fake.FakeInjector
import io.tagd.arch.infra.InfraService
import io.tagd.arch.present.service.PresentationService
import io.tagd.di.Global
import io.tagd.di.layer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TagDiFactoryTest {

    @Before
    fun setup() {
        FakeInjector.inject()
    }

    @After
    fun tearDown() {
        FakeInjector.release()
    }

    @Test
    fun `verify infraService access for bounded service`() {
        with(Global) {
            layer<InfraService> {
                bind<InfraService>().toInstance(mock())
            }
        }

        val service = infraService<InfraService>()
        assert(service != null)
    }

    @Test
    fun `verify presentationService access for bounded service`() {
        with(Global) {
            layer<PresentationService> {
                bind<PresentationService>().toInstance(mock())
            }
        }

        val service = presentationService<PresentationService>()
        assert(service != null)
    }

    @Test
    fun `verify useCase access for bounded service`() {
        with(Global) {
            layer<Command<*, *>> {
                bind<Command<*, *>>().toInstance(mock())
            }
        }

        val service = useCase<Command<*, *>>()
        assert(service != null)
    }

    @Test
    fun `verify domainService access for bounded service`() {
        with(Global) {
            layer<DomainService> {
                bind<DomainService>().toInstance(mock())
            }
        }

        val service = domainService<DomainService>()
        assert(service != null)
    }

    @Test
    fun `verify repository access for bounded service`() {
        with(Global) {
            layer<Repository> {
                bind<Repository>().toInstance(mock())
            }
        }

        val service = repository<Repository>()
        assert(service != null)
    }

    @Test
    fun `verify gateway access for bounded service`() {
        with(Global) {
            layer<Gateway> {
                bind<Gateway>().toInstance(mock())
            }
        }

        val service = gateway<Gateway>()
        assert(service != null)
    }

    @Test
    fun `verify dao access for bounded service`() {
        with(Global) {
            layer<DataAccessObject> {
                bind<DataAccessObject>().toInstance(mock())
            }
        }

        val service = dao<DataAccessObject>()
        assert(service != null)
    }

    @Test
    fun `verify cache access for bounded service`() {
        with(Global) {
            layer<Cache<*>> {
                bind<Cache<*>>().toInstance(mock())
            }
        }

        val service = cache<Cache<*>>()
        assert(service != null)
    }

    @Test
    fun `verify crosscutting access for bounded service`() {
        with(Global) {
            layer<CrossCutting> {
                bind<CrossCutting>().toInstance(mock())
            }
        }

        val service = crosscutting<CrossCutting>()
        assert(service != null)
    }


    @Test
    fun `verify createInfra access for bounded service`() {
        val service1 = FakeInfraService()
        with(Global) {
            layer<InfraService> {
                bind<FakeInfraService>().toCreator { service1 }
            }
        }

        val service2 = createInfra<FakeInfraService>()
        assert(service1 === service2)
    }

    class FakeInfraService : InfraService {
        override fun release() {
        }
    }
}