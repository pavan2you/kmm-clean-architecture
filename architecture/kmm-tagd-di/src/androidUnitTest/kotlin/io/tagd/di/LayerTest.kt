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
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.test.FakeTypedService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LayerTest {

    private val scope = Global
    private val layer = Layer<Service>(scope)
    private val mockService: Service = mock()
    private val mockServiceCreator: (State?) -> Service = mock()

    @Test
    fun `given a Service bound then verify binding working by accessing service`() {
        layer.bind(key(), mockService)
        val result = layer.get<Service>(key<Service>())
        assert(mockService === result)
    }

    @Test
    fun `given a Service bound inline then verify inline binding working by accessing service`() {
        layer.bind<Service>().toInstance(mockService)
        val result = layer.get<Service>(key<Service>())
        assert(mockService === result)
    }

    @Test
    fun `given a known Service then verify Layer#contains() returns true`() {
        layer.bind(key(), mockService)
        val result = layer.contains(key<Service>()) ?: false
        assert(result)
    }

    @Test
    fun `given an unknown Service then verify Layer#contains() returns false`() {
        layer.bind(key(), mockService)
        val key = key<FakeTypedService<String>>()
        val result = layer.contains(key) ?: false
        assert(!result)
    }

    @Test
    fun `given an unknown Service then verify GetService returns null`() {
        val result = layer.get<Service>(key<Service>())
        assert(result == null)
    }

    @Test
    fun `given a service is bound by its creator, then verify binding is successful by checking creator is producing corresponding service object`() {
        layer.bind(key(), mockServiceCreator)

        val state = null
        whenever(mockServiceCreator.invoke(state)).thenReturn(mockService)

        val result = layer.get<Service>(key<Service>())
        assert(mockService === result)

        verify(mockServiceCreator).invoke(state)
    }

    @Test
    fun `given a service is bound inline by its creator, then verify binding is successful by checking creator is producing corresponding service object`() {
        layer.bind<Service>().toCreator(mockServiceCreator)

        val state = null
        whenever(mockServiceCreator.invoke(state)).thenReturn(mockService)

        val result = layer.get<Service>(key<Service>())
        assert(mockService === result)

        verify(mockServiceCreator).invoke(state)
    }


    @Test
    fun `given a creator is available for a Service, then verify Service is created upon calling creator`() {
        layer.bind(key(), mockServiceCreator)

        val state = null
        whenever(mockServiceCreator.invoke(state)).thenReturn(mockService)

        val result = layer.create(key<Service>(), state)
        assert(mockService === result)

        verify(mockServiceCreator).invoke(state)
    }

    @Test
    fun `given Layer#release() is called, then verify services are cleared by checking against null`() {
        layer.bind(key(), mockService)
        val result = layer.get<Service>(key<Service>())
        assert(mockService === result)
        layer.release()
        val postReleaseResult = layer.get<Service>(key<Service>())
        assert(postReleaseResult == null)
    }
}