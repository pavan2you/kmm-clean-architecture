package io.tagd.di/*
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

import io.tagd.di.test.FakeTypedService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.reflect.KClass

@RunWith(MockitoJUnitRunner::class)
class TypedClassTest {

    @Test
    fun `given two typed classes with same state then verify TypedClasses are equal`() {
        val clazz1: KClass<FakeTypedService<String>> = typeOf()
        val type1: KClass<String> = typeOf()

        val clazz2: KClass<FakeTypedService<String>> = typeOf()
        val type2: KClass<String> = typeOf()

        val one: TypedClass<FakeTypedService<String>> = TypedClass(clazz1, arrayOf(type1))
        val two: TypedClass<FakeTypedService<String>> = TypedClass(clazz2, arrayOf(type2))

        assert(one == two)
        assert(one.hashCode() == two.hashCode())

        assert(one.clazz == two.clazz)
        assert(one.typeClasses.contentEquals(two.typeClasses))
    }

    @Test
    fun `given two typed classes with different state then verify TypedClasses are not same`() {
        val clazz1: KClass<FakeTypedService<String>> = typeOf()
        val type1: KClass<String> = typeOf()

        val clazz2: KClass<FakeTypedService<Float>> = typeOf()
        val type2: KClass<Float> = typeOf()

        val one: TypedClass<FakeTypedService<String>> = TypedClass(clazz1, arrayOf(type1))
        val two: TypedClass<FakeTypedService<Float>> = TypedClass(clazz2, arrayOf(type2))

        assert(one != two)
        assert(one.hashCode() != two.hashCode())
    }

    @Test
    fun `given two typed classes with same state then verify TypeClasses are referentially same`() {
        val clazz1: KClass<FakeTypedService<String>> = typeOf()
        val type1: KClass<String> = typeOf()

        val one: TypedClass<FakeTypedService<String>> = TypedClass(clazz1, arrayOf(type1))
        val two: TypedClass<FakeTypedService<String>> = one

        assert(one === two)
    }

}