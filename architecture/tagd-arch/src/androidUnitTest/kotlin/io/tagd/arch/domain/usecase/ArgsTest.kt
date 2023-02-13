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
import io.tagd.core.stateOf
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArgsTest {

    private val state: Args = spy()

    @Test //todo: Duplicate test for the sake of test coverage
    fun `given data is cleared then verify state is empty`() {
        state.put("float", 1.0f)
        assert(!state.isEmpty())

        state.clear()
        assert(state.isEmpty())
    }

    @Test //todo: Duplicate test for the sake of test coverage
    fun `given state is released then verify state is empty`() {
        state.put("float", 1.0f)
        assert(!state.isEmpty())

        state.release()
        assert(state.isEmpty())
    }

    @Test //todo: Duplicate test for the sake of test coverage
    fun `given state of is called then verify state object is created`() {
        val state = stateOf("one" to 1)
        assert(state.get<Int>("one") == 1)
    }

    @Test
    fun `given argsOf is called then verify state object is created`() {
        val state = argsOf("one" to 1)
        assert(state.get<Int>("one") == 1)
    }
}