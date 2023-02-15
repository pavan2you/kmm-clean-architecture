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

package io.tagd.arch.data.cache

import com.nhaarman.mockito_kotlin.spy
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AbstractCacheTest {

    private val cache: AbstractCache<Any> = spy()

    @Test
    fun `given a DataCache then verify it is not null`() {
        Assert.assertNotNull(cache)
    }

    @Test
    fun `given release is called then verify it is handled`() {
        var called = false
        Mockito.`when`(cache.release()).thenAnswer {
            called = true
            return@thenAnswer Unit
        }

        cache.release()

        assert(called)
    }
}