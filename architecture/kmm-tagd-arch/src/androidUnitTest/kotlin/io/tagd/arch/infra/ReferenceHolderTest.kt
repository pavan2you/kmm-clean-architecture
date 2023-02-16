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

package io.tagd.arch.infra

import com.nhaarman.mockito_kotlin.spy
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReferenceHolderTest {

    private val referenceHolder: ReferenceHolder<String> = spy(ReferenceHolder("hello"))

    @Test
    fun `given a Reference then verify it is not null`() {
        assertNotNull(referenceHolder)
    }

    @Test
    fun `given a Reference then verify value is not null`() {
        assertNotNull(referenceHolder.value)
    }

    @Test
    fun `given a Reference then verify value is returned is as expected`() {
        assert(referenceHolder.value == "hello")
    }

    @Test
    fun `given Release is called then verify it is handled`() {
        assertNotNull(referenceHolder.value)
        referenceHolder.release()
        assert(referenceHolder.value == null)
    }
}