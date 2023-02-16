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

package io.tagd.arch.domain.usecase

import com.nhaarman.mockito_kotlin.spy
import io.tagd.arch.fake.FakeUseCaseCaller
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LiveDataTest {

    @Test
    fun `given a new observer when putIfAbsent is called then verify it is added`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(args, triggering, success, failure)

        val liveData = LiveData<String>(args)
        val pastValue = liveData.putIfAbsent(observer)

        assert(liveData.observers.contains(observer))
        assert(pastValue == null)
    }

    @Test
    fun `given existingObserver when putIfAbsent is called then verify it is ignored`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(args, triggering, success, failure)

        val liveData = LiveData<String>(args)
        liveData.addObserver(observer)
        val pastValue = liveData.putIfAbsent(observer)

        assert(pastValue == observer)
    }

    @Test
    fun `given observer is present when removeObserver is called then verify it is removed`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(args, triggering, success, failure)

        val liveData = LiveData<String>(args)
        liveData.addObserver(observer)
        assert(liveData.observers.contains(observer))

        liveData.removeObserver(observer)
        assert(!liveData.observers.contains(observer))
    }

    @Test
    fun `given new observer is added when notifying currentResult is in progress then verify it is ignored`() {
        val caller1 = FakeUseCaseCaller<String>(Args(observe = false))
        val args1 = caller1.args
        val success1 = caller1.success
        val failure1 = caller1.failure
        val triggering1 = caller1.triggering
        val observer1 = LiveObserver<String>(args1, triggering1, success1, failure1)

        val liveData = spy(LiveData<String>(args1))
        liveData.addObserver(observer1)
        assert(liveData.invalidate.get() == true)

        val value = "newValue"
        var firstTime = true

        val caller2 = FakeUseCaseCaller<String>(args1)
        val success2 = caller2.success
        val failure2 = caller2.failure
        val triggering2 = caller2.triggering
        val observer2 = LiveObserver<String>(args1, triggering2, success2, failure2)

        Mockito.`when`(liveData.notifyObservers(liveData.observers, value, 0)).thenAnswer {
            if (firstTime) {
                liveData.addObserver(observer2)
                firstTime = false
            }
            it.callRealMethod()
        }

        liveData.setValue("newValue")

        assert(liveData.observers.isEmpty())
        assert(caller1.result == "newValue")
        assert(caller2.result == "newValue")
    }
}