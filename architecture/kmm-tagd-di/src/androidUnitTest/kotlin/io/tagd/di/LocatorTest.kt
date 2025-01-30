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

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.*
import io.tagd.di.test.FakeService
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocatorTest {

    private val scope: Scope = Global
    private val locator: Locator = LayerLocator(scope)

    @Test
    fun `given a new Layer then verify it is added successfully`() {
        val addedLayer: Layer<FakeService> = Layer(scope, "sample")
        locator.bind(addedLayer, typeOf())

        val contains = locator.layers()?.containsValue(addedLayer) ?: false
        assert(contains)
    }

    @Test
    fun `given a known Layer then verify LayerLocator#locate returns the same`() {
        val addedLayer: Layer<FakeService> = Layer(scope, "sample")
        locator.bind(addedLayer, typeOf())

        val returnedLayer = locator.locate(typeOf<FakeService>())

        assert(addedLayer === returnedLayer)
    }

    @Test
    fun `given an unknown Layer then verify LayerLocator#locate returns null`() {
        val addedLayer: Layer<Service> = Layer(scope, "sample")
        locator.bind(addedLayer, typeOf())

        val returnedLayer = locator.locate(typeOf<FakeService>())

        assert(returnedLayer == null)
        assert(addedLayer != returnedLayer)
    }

    @Test
    fun `given a Layer is already present, then verify layer-inline won't create a new layer`() {
        var callbackTriggered1 = false
        val layer = locator.layer<Service> {
            callbackTriggered1 = true
        }

        var callbackTriggered2 = false
        val existingLayer = locator.layer<Service> {
            callbackTriggered2 = true
        }

        TestCase.assertNotNull(layer)
        assert(callbackTriggered1)
        TestCase.assertNotNull(layer === existingLayer)
        assert(callbackTriggered2)
    }

    @Test
    fun `given an unknown service-key then verify locator returns no service`() {
        val service = locator.get(key<FakeService>())
        assert(service == null)
    }

    @Test
    fun `given an existing service-key then verify locator returns service`() {
        val serviceAdded = FakeService()
        locator.layer<FakeService> {
            bind(key(), serviceAdded)
        }

        val service = locator.get(key<FakeService>())
        assert(serviceAdded === service)
    }

    @Test
    fun `given an existing service-creator and key then verify Locator#create's the service`() {
        val serviceAdded = FakeService()
        val fakeServiceCreator: (State?) -> FakeService = mock()
        val state = null
        whenever(fakeServiceCreator.invoke(state)).thenReturn(serviceAdded)

        locator.layer<FakeService> {
            bind(key(), fakeServiceCreator)
        }

        val service = locator.create(key<FakeService>(), null)
        assert(serviceAdded === service)
        verify(fakeServiceCreator).invoke(state)
    }

    @Test
    fun `given release is called then verify locator state is nullified`() {
        val layer: Layer<Service> = Layer(scope, "sample")
        locator.bind(layer, typeOf())

        locator.release()

        TestCase.assertNull(locator.layers())
    }
}