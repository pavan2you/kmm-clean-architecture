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

import io.tagd.arch.fake.FakeUseCaseCaller
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class LiveObserverTest {

    @Test
    fun `given observer's onError is called then verify exception is dispatched to caller`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(args, triggering, success, failure)

        observer.onError(RuntimeException("Some exception"))

        assert(caller.error != null)
    }

    @Test
    fun `given observer's onChange is called then verify success is dispatched to caller`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(args, triggering, success, failure)

        observer.onChange("newValue", 1)

        assert(caller.result ==  "newValue")
    }

    @Test
    fun `given observer's onChange is called then verify success is not called for result lesser than current version`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(args, triggering, success, failure)
        observer.resultVersion = 2

        observer.onChange("newValue", 1)

        assert(caller.result == null)
    }

    @Test
    fun `given observer's onChange is called then verify result is not memorised if arg set for not to observe`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(args, triggering, success, failure)

        observer.onChange("newValue", 1)

        assert(observer.result == null)
    }

    @Test
    fun `given observer's onChange is called then verify result is memorised if arg set for observe`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(args, triggering, success, failure)

        observer.onChange("newValue", 1)

        assert(observer.result != null)
    }

    @Test
    fun `given observer's onChange is called then verify result is memorised if arg are null`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer = LiveObserver<String>(null, triggering, success, failure)

        observer.onChange("newValue", 1)

        assert(observer.result != null)
        assert(caller.result != null)
    }

    @Test
    fun `given two observers with same parameters then verify they are equal`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer1 = LiveObserver<String>(args, triggering, success, failure)
        val observer2 = LiveObserver<String>(args, triggering, success, failure)

        assert(observer1 == observer2)
    }

    @Test
    fun `given two observers with same parameters then verify they are not identically equal`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer1 = LiveObserver<String>(args, triggering, success, failure)
        val observer2 = LiveObserver<String>(args, triggering, success, failure)

        assert(observer1 !== observer2)
    }

    @Test
    fun `given two observers with same parameters then verify their hash codes are same`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer1 = LiveObserver<String>(args, triggering, success, failure)
        val observer2 = LiveObserver<String>(args, triggering, success, failure)

        assert(observer1.hashCode() == observer2.hashCode())
    }

    @Test
    fun `given two observers with some similar parameters then verify they are not equal`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val triggering = caller.triggering
        val observer1 = LiveObserver<String>(args, triggering, success, failure)
        val observer2 = LiveObserver<String>(null, triggering, success, failure)

        assert(observer1 != observer2)
    }

    @Test
    fun `given no scuccess callback then verify result is not dispatched`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val failure = caller.failure
        val observer = LiveObserver<String>(null, null, null, failure)

        observer.onChange("newValue", 1)

        assert(observer.result != null)
        assert(caller.result == null)
    }

    @Test
    fun `given no failure callback then verify exception is not dispatched`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val success = caller.success
        val observer = LiveObserver<String>(null, null, success, null)

        observer.onError(RuntimeException("Exception"))

        assert(observer.result == null)
        assert(caller.error == null)
    }

    @Test
    fun `given no callbacks then verify results are not dispatched`() {
        val caller = FakeUseCaseCaller<String>(Args())
        val observer = LiveObserver<String>(null, null, null, null)

        observer.onChange("newValue", 1)
        observer.onError(RuntimeException("Exception"))

        assert(caller.result == null)
        assert(caller.error == null)
    }

    @Test
    fun givenArgsThenVerifyArgs() {
        val observer = LiveObserver<String>(null, null, null, null)

        observer.onChange("newValue", 1)
        observer.onError(RuntimeException("Exception"))

        assert(observer.invalidating == null)
        assert(observer.success == null)
        assert(observer.failure == null)
        assert(observer.args == null)
    }
}