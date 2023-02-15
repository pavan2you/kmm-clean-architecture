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

package io.tagd.arch.present.mvb

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import io.tagd.arch.data.DataObject
import io.tagd.arch.fake.FakeDataBinder
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataBinderTest {

    private val view: BindableView<DataObject> = mock()
    private val binder = spy(FakeDataBinder(view))

    @Test
    fun `verify view is not null`() {
        assert(binder.view != null)
    }

    @Test
    fun `verify view is released`() {
        binder.release()
        assert(binder.view == null)
    }

    @Test
    fun `verify onCreate is called`() {
        var called = false
        Mockito.`when`(binder.onCreate()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        binder.onCreate()

        assert(called)
    }

    @Test
    fun `verify onBind is called`() {
        var called = false
        val dataObject = mock<DataObject>()
        Mockito.`when`(binder.onBind(dataObject)).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        binder.onBind(dataObject)

        assert(called)
    }

    @Test
    fun `verify onUnBind is called`() {
        var called = false
        Mockito.`when`(binder.onUnbind()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        binder.onUnbind()

        assert(called)
    }

    @Test
    fun `verify onDestroy is called`() {
        var called = false
        Mockito.`when`(binder.onDestroy()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        binder.onDestroy()

        assert(called)
    }

}