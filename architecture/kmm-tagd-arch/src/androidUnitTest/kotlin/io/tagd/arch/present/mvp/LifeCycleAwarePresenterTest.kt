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

package io.tagd.arch.present.mvp

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LifeCycleAwarePresenterTest {

    private val view: PresentableView = mock()
    private val presenter = spy(LifeCycleAwarePresenter(view))

    @Test
    fun `verify view is not null`() {
        assert(presenter.view != null)
    }

    @Test
    fun `verify view is released`() {
        presenter.release()
        assert(presenter.view == null)
    }

    @Test
    fun `verify onCreate is called`() {
        var called = false
        `when`(presenter.onCreate()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onCreate()

        assert(called)
    }

    @Test
    fun `verify onStart is called`() {
        var called = false
        `when`(presenter.onStart()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onStart()

        assert(called)
    }

    @Test
    fun `verify onResume is called`() {
        var called = false
        `when`(presenter.onResume()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onResume()

        assert(called)
    }

    @Test
    fun `verify onAwaiting is called`() {
        var called = false
        `when`(presenter.onAwaiting()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onAwaiting()

        assert(called)
    }

    @Test
    fun `verify onReady is called`() {
        var called = false
        `when`(presenter.onReady()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onReady()

        assert(called)
    }

    @Test
    fun `verify onPause is called`() {
        var called = false
        `when`(presenter.onPause()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onPause()

        assert(called)
    }

    @Test
    fun `verify onStop is called`() {
        var called = false
        `when`(presenter.onStop()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onStop()

        assert(called)
    }

    @Test
    fun `verify onDestroy is called`() {
        var called = false
        `when`(presenter.onDestroy()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onDestroy()

        assert(called)
    }

    @Test
    fun `verify onBackPressed is called`() {
        var called = false
        `when`(presenter.onBackPressed()).thenAnswer {
            it.callRealMethod()
            called = true
            return@thenAnswer Unit
        }

        presenter.onBackPressed()

        assert(called)
    }

    @Test
    fun `given canHandleBackPress is null then verify result is false`() {
        presenter.canHandleBackPress = null
        val result = presenter.canHandleBackPress()
        assert(!result)
    }

    @Test
    fun `given canHandleBackPress is non null then verify result is value`() {
        val value = presenter.canHandleBackPress
        val result = presenter.canHandleBackPress()
        assert(result == value)
    }
}