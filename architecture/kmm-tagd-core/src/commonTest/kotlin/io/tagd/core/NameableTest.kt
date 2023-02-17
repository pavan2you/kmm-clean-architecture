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

package io.tagd.core

import io.tagd.core.fake.FakeNameable
import kotlin.test.Test
import kotlin.test.assertTrue

class NameableTest {

    private val nameable: Nameable = FakeNameable()

    @Test
    fun `given a nameable verify it has a name`() {
        nameable as FakeNameable
        assertTrue(nameable.name != null)
    }

    @Test
    fun `given a nameable verify it has a name matching to given name`() {
        nameable as FakeNameable
        assertTrue(nameable.name === "fake")
    }
}