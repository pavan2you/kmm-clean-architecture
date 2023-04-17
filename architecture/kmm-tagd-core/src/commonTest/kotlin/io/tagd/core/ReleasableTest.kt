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

import io.tagd.core.test.FakeLeakableReleasableUsageClientSite
import io.tagd.core.test.FakeReadOnlyReleasableClientSite
import io.tagd.core.test.FakeReleasable
import io.tagd.core.test.FakeReleasableOwnerClientSite
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertTrue

class ReleasableTest {

    private val releasable: Releasable = FakeReleasable()

    private val releasableOwnerClientSite = FakeReleasableOwnerClientSite()
    private val releasableUserClientSite = FakeReadOnlyReleasableClientSite(releasable)
    private val leakableReleasableUserClientSite = FakeLeakableReleasableUsageClientSite(releasable)

    @Test
    fun `given release is called then verify object state is nullified`() {
        releasable as FakeReleasable
        assertTrue(releasable.nullableValue != null)
        releasable.release()
        assertTrue(releasable.nullableValue == null)
    }

    @Test
    fun `given read only client calls dispatchRelease then verify the referred relesable is nullified but not its state`() {
        releasable as FakeReleasable
        assertTrue(releasable.nullableValue != null)

        releasableUserClientSite.dispatchRelease()
        leakableReleasableUserClientSite.dispatchRelease()

        assertTrue(releasableUserClientSite.mutableReleasable == null)
        assertTrue(leakableReleasableUserClientSite.nullableReleasable == null)
        assertTrue(releasable.nullableValue != null)
    }

    @Test
    fun `given client is a relesable owner when client's dispatchRelease is called then verify created relesable is released and is nullified`() {
        assertTrue(releasableOwnerClientSite.nullableReleasable != null)

        releasableOwnerClientSite.dispatchRelease()

        assertTrue(releasableOwnerClientSite.nullableReleasable == null)
    }

    @Test
    fun `given client calls dispatchesRelease then verify relesable access throws exception`() {
        releasable as FakeReleasable
        assertTrue(releasable.nullableValue != null)
        assertTrue(releasableOwnerClientSite.nullableReleasable != null)

        val exception = assertFails {
            releasableOwnerClientSite.dispatchRelease()

            releasableOwnerClientSite.releasable.toString()
            releasableUserClientSite.releasable.toString()
            leakableReleasableUserClientSite.releasable.toString()
        }
        assertTrue(exception is NullPointerException)
    }
}