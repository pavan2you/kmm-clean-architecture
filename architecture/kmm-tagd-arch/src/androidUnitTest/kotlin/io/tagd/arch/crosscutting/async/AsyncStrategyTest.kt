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

package io.tagd.arch.crosscutting.async

import io.tagd.arch.crosscutting.async.cacheIO
import io.tagd.arch.crosscutting.async.cancelAsync
import io.tagd.arch.crosscutting.async.compute
import io.tagd.arch.crosscutting.async.daoIO
import io.tagd.arch.crosscutting.async.diskIO
import io.tagd.arch.crosscutting.async.networkIO
import io.tagd.arch.crosscutting.async.present
import io.tagd.arch.test.FakeAsyncStrategy
import io.tagd.arch.test.FakeInjector
import io.tagd.core.AsyncContext
import io.tagd.core.AsyncStrategy
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AsyncStrategyTest : AsyncContext {

    private lateinit var asyncStrategy: AsyncStrategy

    @Before
    fun setup() {
        FakeInjector.inject()
        asyncStrategy = FakeAsyncStrategy()
    }

    @After
    fun tearDown() {
        FakeInjector.release()
    }

    @Test
    fun `verify execute calls the work`() {
        var executed = false
        asyncStrategy.execute {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify execute calls the work with context`() {
        var executed = false
        asyncStrategy.execute(this) {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify cancel is successful`() {
        val result = asyncStrategy.cancel(this)
        assert(result)
    }

    @Test
    fun `verify release is successful`() {
        asyncStrategy.release()
        assert((asyncStrategy as FakeAsyncStrategy).released)
    }

    @Test
    fun `verify computation work without context`() {
        var executed = false
        compute {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify computation work with context`() {
        var executed = false
        compute {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify presentation work without context`() {
        var executed = false
        present {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify presentation work with context`() {
        var executed = false
        present {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify networkIO work without context`() {
        var executed = false
        networkIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify networkIO work with context`() {
        var executed = false
        networkIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify diskIO work without context`() {
        var executed = false
        diskIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify diskIO work with context`() {
        var executed = false
        diskIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify daoIO work without context`() {
        var executed = false
        daoIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify daoIO work with context`() {
        var executed = false
        daoIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify cacheIO work without context`() {
        var executed = false
        cacheIO {
            executed = true
        }
        assert(executed)
    }

    @Test
    fun `verify cacheIO work with context`() {
        var executed = false
        cacheIO {
            executed = true
        }
        assert(executed)
    }

    override fun release() {
        cancelAsync()
    }
}