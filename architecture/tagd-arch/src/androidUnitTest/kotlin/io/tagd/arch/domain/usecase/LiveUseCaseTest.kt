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

import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.spy
import io.tagd.arch.fake.FakeUseCaseCaller
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LiveUseCaseTest {

    private val useCase: LiveUseCase<String> = spy()
    private val executionMonitor = useCase.executionMonitor

    @After
    fun tearDown() {
        reset(useCase)
    }

    @Test
    fun `given args and callbacks when execution is success then verify invoke success callback`() {
        val caller = FakeUseCaseCaller<String>()
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val result = "result"

        `when`(useCase.execute(args, success, failure)).thenAnswer {
            it.callRealMethod()
            it.getArgument<Callback<String>>(1)?.invoke(result)
        }
        useCase.execute(args, success, failure)

        assert(caller.result == result)
    }

    @Test
    fun `given args and callbacks when execution fails then verify invoke error callback`() {
        val caller = FakeUseCaseCaller<String>()
        val args = caller.args
        val success = caller.success
        val failure = caller.failure

        `when`(useCase.execute(args, success, failure)).thenAnswer {
            it.callRealMethod()
            it.getArgument<Callback<Throwable>>(2)?.invoke(RuntimeException())
        }
        useCase.execute(args, success, failure)

        assert(caller.error != null)
    }

    @Test
    fun `given args and callbacks when execution success then verify result is cached by default`() {
        val caller = FakeUseCaseCaller<String>()
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val result = "result"

        `when`(useCase.execute(args, success, failure)).thenAnswer {
            it.callRealMethod()
            useCase.setValue(args, result)
        }
        useCase.execute(args, success, failure)

        assert(caller.result == result)
        assert(useCase.lastResult(args) == result)
    }

    @Test
    fun `given same args with different callbacks then verify all callbacks will receive same result`() {
        val caller1 = FakeUseCaseCaller<String>()
        val args1 = caller1.args
        val success1 = caller1.success
        val failure1 = caller1.failure
        val result1 = "result1"

        val caller2 = FakeUseCaseCaller<String>(args1)
        val success2 = caller2.success
        val failure2 = caller2.failure
        val result2 = "result2"

        `when`(useCase.execute(args1, success1, failure1)).thenAnswer {
            it.callRealMethod()
            useCase.setValue(args1, result1)
        }
        `when`(useCase.execute(args1, success2, failure2)).thenAnswer {
            it.callRealMethod()
            useCase.setValue(args1, result2)
        }

        //this is to clear off the state set by `when`(useCase.execute(args, success, failure))
        useCase.cancel()

        useCase.execute(args1, success1, failure1)
        assert(caller1.result == result1)
        assert(useCase.lastResult(args1) == result1)

        useCase.execute(args1, success2, failure2)
        assert(caller1.result == result2)
        assert(caller2.result == result2)
        assert(useCase.lastResult(args1) == result2)
    }

    @Test
    fun `given no args with different callbacks then verify all callbacks will receive same result`() {
        val caller1 = FakeUseCaseCaller<String>(null)
        val args1 = caller1.args
        val success1 = caller1.success
        val failure1 = caller1.failure
        val result1 = "result1"

        val caller2 = FakeUseCaseCaller<String>(null)
        val success2 = caller2.success
        val failure2 = caller2.failure
        val result2 = "result2"

        `when`(useCase.execute(args1, success1, failure1)).thenAnswer {
            it.callRealMethod()
            useCase.setValue(args1, result1)
        }
        `when`(useCase.execute(args1, success2, failure2)).thenAnswer {
            it.callRealMethod()
            useCase.setValue(args1, result2)
        }

        //this is to clear off the state set by `when`(useCase.execute(args, success, failure))
        useCase.cancel()

        useCase.execute(args1, success1, failure1)
        assert(caller1.result == result1)
        assert(useCase.lastResult(args1) == result1)

        useCase.execute(args1, success2, failure2)
        assert(caller1.result == result2)
        assert(caller2.result == result2)
        assert(useCase.lastResult(args1) == result2)
    }

    @Test
    fun `given multiple calls with same args and callbacks when execution success then verify allButFirstResult is from cache`() {
        val caller = FakeUseCaseCaller<String>()
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val result = "result"

        `when`(useCase.execute(args, success, failure)).thenAnswer {
            it.callRealMethod()
            if (executionMonitor.isLastExecutionFromTrigger(args)) {
                useCase.setValue(args, result)
            }
        }

        //this is to clear off the state set by `when`(useCase.execute(args, success, failure))
        useCase.cancel()

        useCase.execute(args, success, failure)
        assert(executionMonitor.isLastExecutionFromTrigger(args))

        useCase.execute(args, success, failure)
        assert(executionMonitor.isLastExecutionFromCache(args))

        useCase.execute(args, success, failure)
        assert(executionMonitor.isLastExecutionFromCache(args))
    }

    @Test
    fun `given multiple calls with same args and callbacks when execution is waiting then verify allButFirstCall are ignored`() {
        val caller = FakeUseCaseCaller<String>()
        val args = caller.args
        val success = caller.success
        val failure = caller.failure

        `when`(useCase.execute(args, success, failure)).thenAnswer {
            it.callRealMethod()
        }

        //this is to clear off the state set by `when`(useCase.execute(args, success, failure))
        useCase.cancel()

        useCase.execute(args, success, failure)
        assert(executionMonitor.isLastExecutionFromTrigger(args))

        useCase.execute(args, success, failure)
        assert(executionMonitor.isLastExecutionIgnored(args))

        useCase.execute(args, success, failure)
        assert(executionMonitor.isLastExecutionIgnored(args))
    }

    @Test
    fun `given a non cacheable args and callbacks when execution is success then verify execution monitor is removed`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val result = "result"

        `when`(useCase.execute(args, success, failure)).thenAnswer {
            it.callRealMethod()
            if (executionMonitor.isLastExecutionFromTrigger(args)) {
                useCase.setValue(args, result)
            }
        }

        //this is to clear off the state set by `when`(useCase.execute(args, success, failure))
        useCase.cancel()

        useCase.execute(args, success, failure)
        assert(!executionMonitor.isHavingExecutionMonitor(args))
    }

    @Test
    fun `given a non cacheable args and callbacks when execution fails then verify execution monitor is removed`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args
        val success = caller.success
        val failure = caller.failure

        `when`(useCase.execute(args, success, failure)).thenAnswer {
            it.callRealMethod()
            if (executionMonitor.isLastExecutionFromTrigger(args)) {
                useCase.setError(args, Exception())
            }
        }

        //this is to clear off the state set by `when`(useCase.execute(args, success, failure))
        useCase.cancel()

        useCase.execute(args, success, failure)
        assert(!executionMonitor.isHavingExecutionMonitor(args))
    }

    @Test
    fun `given cancel is called then verify all executions are cleared`() {
        val caller1 = FakeUseCaseCaller<String>(null)
        val args1 = caller1.args
        val success1 = caller1.success
        val failure1 = caller1.failure

        val caller2 = FakeUseCaseCaller<String>(null)
        val args2 = caller2.args
        val success2 = caller2.success
        val failure2 = caller2.failure

        useCase.execute(args1, success1, failure1)
        useCase.execute(args2, success2, failure2)
        useCase.cancel()

        assert(!useCase.isActive())
    }

    @Test
    fun `given cancel is called with context then verify all executions are cleared with context only`() {
        val caller1 = FakeUseCaseCaller<String>(null)
        val args1 = caller1.args
        val success1 = caller1.success
        val failure1 = caller1.failure

        val caller2 = FakeUseCaseCaller<String>(Args(context = "test"))
        val args2 = caller2.args
        val success2 = caller2.success
        val failure2 = caller2.failure

        useCase.execute(args1, success1, failure1)
        useCase.execute(args2, success2, failure2)
        useCase.cancel(context = "test")

        assert(!useCase.isActive(context = "test"))
        assert(useCase.isActive())
    }

    @Test
    fun `given no active observers then verify is active is false`() {
        assert(!useCase.isActive())
    }

    @Test
    fun `given active observers then verify is active is true`() {
        val caller1 = FakeUseCaseCaller<String>(null)
        val args1 = caller1.args
        val success1 = caller1.success
        val failure1 = caller1.failure

        val caller2 = FakeUseCaseCaller<String>(Args(context = "test"))
        val args2 = caller2.args
        val success2 = caller2.success
        val failure2 = caller2.failure

        useCase.execute(args1, success1, failure1)
        useCase.execute(args2, success2, failure2)

        assert(useCase.isActive())
    }

    @Test
    fun `given a not matching context then verify is active is false`() {
        val caller1 = FakeUseCaseCaller<String>(null)
        val args1 = caller1.args
        val success1 = caller1.success
        val failure1 = caller1.failure

        val caller2 = FakeUseCaseCaller<String>(Args(context = "test"))
        val args2 = caller2.args
        val success2 = caller2.success
        val failure2 = caller2.failure

        useCase.execute(args1, success1, failure1)
        useCase.execute(args2, success2, failure2)

        assert(!useCase.isActive(context = "someNotFoundContext"))
    }

    @Test
    fun `given release is called then verify state is released`() {
        useCase.release()

        assert(!useCase.isActive())
    }

    @Test
    fun `given trigger is called verify it is handled`() {
        val caller = FakeUseCaseCaller<String>(Args(observe = false))
        val args = caller.args!!

        `when`(useCase.trigger(args)).thenAnswer {
            args.put("called", true)
        }

        useCase.trigger(args)

        assert(args.get<Boolean>("called") == true)
    }

    @Test
    fun `given invalidate is called verify it is handled`() {
        val args = argsOf()
        val caller1 = FakeUseCaseCaller<String>(args)
        val success1 = caller1.success
        val failure1 = caller1.failure

        val caller2 = FakeUseCaseCaller<String>(args)
        val success2 = caller2.success
        val failure2 = caller2.failure

        val result = "result"

        useCase.resolveArgsAndObserver(args, null, success1, failure1)
        useCase.resolveArgsAndObserver(args, null, success2, failure2)

        `when`(useCase.trigger(args)).thenAnswer {
            if (executionMonitor.isLastExecutionFromInvalidation(args)) {
                useCase.setValue(args, result)
            }
        }

        useCase.invalidate()

        assert(caller1.result == result)
        assert(useCase.lastResult(args) == result)

        assert(caller2.result == result)
        assert(useCase.lastResult(args) == result)
    }

    @Test
    fun `given flush is called verify observer is removed`() {
        val caller = FakeUseCaseCaller<String>()
        val args = caller.args
        val success = caller.success
        val failure = caller.failure
        val result = "result"

        `when`(useCase.execute(args, success, failure)).thenAnswer {
            it.callRealMethod()
            useCase.setValue(args, result)
        }
        useCase.execute(args, success, failure)

        assert(caller.result == result)
        assert(useCase.lastResult(args) == result)

        useCase.flush(args!!)
        assert(useCase.lastResult(args) == null)
    }
}