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

package io.tagd.arch.data

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataObjectTest {

    @Test
    fun `given a DataObject is created then verify it is not null`() {
        val dataObject = DataObject()
        Assert.assertNotNull(dataObject)
        Assert.assertNotNull(dataObject.crudOperation)
    }

    @Test
    fun `given initialize is called then verify object is having default state`() {
        val dataObject = DataObject()
        dataObject.initialize()
        assert(dataObject.crudOperation == DataObject.CrudOperation.CREATE)
        assert(dataObject.bindables.isEmpty())
    }
}