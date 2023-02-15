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

package io.tagd.arch.data.bind

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BindableSubjectTest {

    @Test
    fun `given a BindableSubject is created then verify it is not null`() {
        val subject = BindableSubject()
        Assert.assertNotNull(subject)
    }

    @Test
    fun `given initialize is called then verify object is having default state`() {
        val subject = BindableSubject()
        subject.initialize()
        assert(subject.bindables.isEmpty())
    }

    @Test
    fun `given add is called then verify bindable is added`() {
        val bindable = mock<Bindable<BindableSubject>>()
        val dataObject = BindableSubject()
        dataObject.add(bindable)
        val found = dataObject.bindables.firstOrNull {
            it.get() == bindable
        }
        Assert.assertNotNull(found)
    }

    @Test
    fun `given remove called then verify bindable is removed`() {
        val bindable = mock<Bindable<BindableSubject>>()
        val dataObject = BindableSubject()
        dataObject.add(bindable)
        assert(dataObject.bindables.size == 1)
        dataObject.remove(bindable)
        assert(dataObject.bindables.size == 0)
    }

    @Test
    fun `verify removeAll removing all bindables`() {
        val bindable = mock<Bindable<BindableSubject>>()
        val dataObject = BindableSubject()
        dataObject.add(bindable)
        assert(dataObject.bindables.size == 1)
        dataObject.removeAllBindables()
        assert(dataObject.bindables.size == 0)
    }

    @Test
    fun `verify addBindableFrom adding bindables from source`() {
        val bindable = mock<Bindable<BindableSubject>>()
        val dataObject1 = BindableSubject()
        dataObject1.add(bindable)
        assert(dataObject1.bindables.size == 1)

        val dataObject2 = BindableSubject()
        dataObject2.addBindablesFrom(dataObject1)
        assert(dataObject2.bindables.size == 1)
    }

    @Test
    fun `verify switchBindingsTo to given target subject`() {
        val bindable = mock<Bindable<BindableSubject>>()
        val subject1 = BindableSubject()
        subject1.add(bindable)
        assert(subject1.bindables.size == 1)

        val subject2 = BindableSubject()
        subject1.switchBindingsTo(subject2)
        assert(subject2.bindables.size == 1)
    }

    @Test
    fun `verify notifyBindables triggering bindTo`() {
        val bindable = mock<Bindable<BindableSubject>>()
        val dataObject = BindableSubject()
        dataObject.add(bindable)

        var called = false
        whenever(bindable.bindTo(dataObject)).thenAnswer {
            called = true
            return@thenAnswer Unit
        }

        dataObject.notifyBindables()
        verify(bindable).bindTo(any())
    }
}