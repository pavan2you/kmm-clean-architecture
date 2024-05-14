package io.tagd.di/*
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

import com.nhaarman.mockito_kotlin.mock
import io.tagd.di.*
import io.tagd.di.test.FakeService
import io.tagd.di.test.FakeTypedService
import io.tagd.di.test.getInject
import io.tagd.di.test.getInjectX
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.reflect.KProperty
import io.tagd.langx.IllegalAccessException

@RunWith(MockitoJUnitRunner::class)
class DelegateTest {

    @Test
    fun `verify inject provides a valid delegate`() {
        val delegateProvider: InjectDelegateProvider<FakeService> = inject()
        TestCase.assertNotNull(delegateProvider)
    }

    @Test
    fun `given an existing service and default scope then verify inject-inline returns service`() {
        stubFakeServiceInDefaultScope()

        val service: FakeService = getInject()
        TestCase.assertNotNull(service)
    }

    @Test
    fun `given an existing service with default scope then verify injectX-inline returns service`() {
        stubFakeServiceInDefaultScope()

        val service: FakeService? = getInjectX()
        TestCase.assertNotNull(service)
    }

    @Test
    fun `given an existing service and scope then verify inject-inline returns service`() {
        stubFakeService()

        val service: FakeService = getInject("test")
        TestCase.assertNotNull(service)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an existing service with unknown scope as default then verify inject-inline throws IllegalAccessException`() {
        Scope("unknown").apply {
            layer<FakeTypedService<*>> {
                bind(key(), FakeTypedService<String>())
            }
        }

        val ignored: FakeTypedService<String> = getInject("unknown")
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an existing service with unknown scope as default then verify injectX-inline throws IllegalAccessException`() {
        Scope("unknown").apply {
            layer<FakeTypedService<*>> {
                bind(key(), FakeTypedService<String>())
            }
        }

        val ignored: FakeTypedService<String>? = getInjectX("unknown")
    }
    @Test
    fun `given an existing service then verify injectX-inline returns service`() {
        stubFakeService()

        val service: FakeService? = getInjectX("test")
        TestCase.assertNotNull(service)
    }

    fun `given an unknown service then verify inject-inline returns service`() {
        stubTestScope()
        val ignored: FakeService = getInject("test")
        assert(ignored != null)
    }

    fun `given an unknown service then verify injectX-inline returns service`() {
        stubTestScope()
        val ignored: FakeService? = getInjectX("test")
        assert(ignored != null)
    }

    @Test
    fun `given an existing service then verify injectX-inline can nullify the value`() {
        stubFakeService()

        val delegateProvider: NullableInjectDelegateProvider<FakeService> = injectX("test")
        val kProperty: KProperty<*> = mock()
        val delegate = delegateProvider.provideDelegate(this, kProperty)

        val service: FakeService? = delegate.getValue(this, kProperty)
        TestCase.assertNotNull(service)
        delegate.setValue(this, kProperty, null)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an existing service when injectX-inline nullifies value then verify accessing it throws IllegalAccessException`() {
        stubFakeService()

        val delegateProvider: NullableInjectDelegateProvider<FakeService> = injectX("test")
        val kProperty: KProperty<*> = mock()
        val delegate = delegateProvider.provideDelegate(this, kProperty)

        val service: FakeService? = delegate.getValue(this, kProperty)
        TestCase.assertNotNull(service)
        delegate.setValue(this, kProperty, null)
        val valueAfterReleased = delegate.getValue(this, kProperty)
        assert(valueAfterReleased == null)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an existing service then verify injectX-inline throws IllegalAccessException if value modifies`() {
        stubFakeService()

        val delegateProvider: NullableInjectDelegateProvider<FakeService> = injectX("test")
        val kProperty: KProperty<*> = mock()
        val delegate = delegateProvider.provideDelegate(this, kProperty)

        val service: FakeService? = delegate.getValue(this, kProperty)
        TestCase.assertNotNull(service)
        delegate.setValue(this, kProperty, FakeService())
    }

    private fun stubFakeService() {
        scope("test") {
            layer<FakeService> {
                bind(key(), FakeService())
            }
        }
    }

    private fun stubFakeServiceInDefaultScope() {
        Global.locator.layer<FakeService> {
            bind(key(), FakeService())
        }
    }

    private fun stubTestScope() {
        scope("test") {
        }
    }
}