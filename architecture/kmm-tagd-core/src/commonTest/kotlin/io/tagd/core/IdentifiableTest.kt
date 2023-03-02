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

import io.tagd.core.fake.*
import kotlin.test.Test
import kotlin.test.assertTrue

class IdentifiableTest {

    private val identifiable: Identifiable<String> = FakeIdentifiable("id1")

    @Test
    fun `given an Identifiable then verify identifier is present`() {
        assertTrue(identifiable.identifier.isNotEmpty())
    }

    @Test
    fun `given two Identifiables then verify identifiers are not same`() {
        val identifiable1 = FakeIdentifiable("id1")
        val identifiable2 = FakeIdentifiable("id2")

        assertTrue(identifiable1.identifier != identifiable2.identifier)
    }
}