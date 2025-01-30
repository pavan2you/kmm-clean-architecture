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
import io.tagd.di.test.FakeService
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import io.tagd.langx.IllegalAccessException

@RunWith(MockitoJUnitRunner::class)
class ScopeTest {

    private val scope = Scope()

    @Test
    fun `verify global state is empty`() {
        assert(scope.state.isEmpty())
    }

    @Test
    fun `given a state with values then verify they are accessible`() {
        val state = State()
        state.put("one", 1)
        state.put("label", "name")

        scope.with(state)

        assert(scope.state == state)
        assert(scope.state.get<Int>("one") == 1)
        assert(scope.state.get<String>("label") == "name")
    }

    @Test
    fun `given a sub scope then verify it is added successfully`() {
        val subScope = Scope("subScope")

        scope.addSubScope(subScope)

        assert(scope.subScope("subScope") == subScope)
    }

    @Test
    fun `given a sub scope name then verify it is removed if exists`() {
        val subScope = Scope("subScope")

        scope.addSubScope(subScope)
        scope.removeSubScope("subScope")

        assert(scope.subScope("subScope") == null)
    }

    @Test
    fun `given a sub scope with nested scopes when a scope name is given then verify it is found`() {
        val subScope = Scope("subScope")
        val innerSupScope = Scope("InnerSubScope")
        subScope.addSubScope(innerSupScope)

        scope.addSubScope(subScope)

        assert(scope.subScope("InnerSubScope") == innerSupScope)
    }

    @Test
    fun `given a sub scope with nested scopes when a scope name is given then verify it is removed successfully`() {
        val subScope = Scope("subScope")
        val innerSupScope = Scope("InnerSubScope")
        subScope.addSubScope(innerSupScope)

        scope.addSubScope(subScope)
        val removed = scope.removeSubScope("InnerSubScope")

        assert(removed == innerSupScope)
        assert(scope.subScope("InnerSubScope") == null)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given a global scope when trying to add then should throw IllegalAccessException`() {
        val subScope = Scope()
        scope.addSubScope(subScope)
    }

    @Test
    fun `given reset is called then verify it is handled by resetting the state of scope`() {
        val subScope = Scope("subScope")
        scope.addSubScope(subScope)
        assert(scope.subScope("subScope") == subScope)

        scope.reset()

        assert(scope.subScope("subScope") == null)
        scope.locator
    }

    @Test(expected = NullPointerException::class)
    fun `given release is called then verify it is releasing the state of scope`() {
        val subScope = Scope("subScope")
        scope.addSubScope(subScope)
        assert(scope.subScope("subScope") == subScope)

        scope.release()

        assert(scope.subScope("subScope") == null)
        scope.locator
    }

    @Test
    fun `given layer inline is called, if it is a new layer, then verify Layer is created`() {
        var callbackTriggered = false
        val layer = scope.layer<Service> {
            callbackTriggered = true
        }
        assertNotNull(layer)
        assert(callbackTriggered)
    }

    @Test
    fun `given layer inline is called, if it is an existing layer, then verify new Layer is not created`() {
        var callbackTriggered1 = false
        val layer = scope.layer<Service> {
            callbackTriggered1 = true
        }

        var callbackTriggered2 = false
        val existingLayer = scope.layer<Service> {
            callbackTriggered2 = true
        }

        assertNotNull(layer)
        assert(callbackTriggered1)
        assertNotNull(layer === existingLayer)
        assert(callbackTriggered2)
    }

    @Test
    fun `given an unknown service-key then verify scope returns no service`() {
        val subScope = Scope("subScope")
        scope.addSubScope(subScope)

        val service = scope.get(key<FakeService>())
        assert(service == null)
    }

    @Test
    fun `given an existing service-key then verify scope returns service`() {
        val serviceAdded = FakeService()
        scope.layer<FakeService> {
            bind(key(), serviceAdded)
        }

        val service = scope.get(key<FakeService>())
        assert(serviceAdded === service)
    }

    @Test
    fun `given an existing nested service-key then verify scope returns the service`() {
        val subScope = Scope("subScope")
        scope.addSubScope(subScope)

        val serviceAdded = FakeService()
        subScope.layer<FakeService> {
            bind(key(), serviceAdded)
        }

        val service = scope.get(key<FakeService>())
        assert(serviceAdded === service)
    }

    @Test
    fun `given a new scope name then verify scope-inline is creating a scope`() {
        var created = false
        val scope = scope("outer") {
            created = true
        }
        assertNotNull(scope)
        assert(created)
    }

    @Test
    fun `given a scope name is global then verify scope-inline returns global scope`() {
        var created = false
        val scope = scope("global") {
            created = true
        }
        assertNotNull(scope)
        assert(scope.name == Scope.GLOBAL_SCOPE)
        assert(created)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given global scope added as sub scope then verify scope-inline throws IllegalAccessException`() {
        scope("global", scope) {
        }
    }

    @Test
    fun `given an existing service-creator and key then verify scope returns service`() {
        val serviceAdded = FakeService()
        val fakeServiceCreator: (State?) -> FakeService = mock()
        val state = null
        whenever(fakeServiceCreator.invoke(state)).thenReturn(serviceAdded)

        scope.layer<FakeService> {
            bind(key(), fakeServiceCreator)
        }

        val service = scope.create(key<FakeService>(), null)
        assert(serviceAdded === service)
        verify(fakeServiceCreator).invoke(state)
    }
}