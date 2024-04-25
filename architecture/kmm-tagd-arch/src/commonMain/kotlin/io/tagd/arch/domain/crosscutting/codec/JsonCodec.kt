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

@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.arch.domain.crosscutting.codec

import io.tagd.langx.reflection.Type
import kotlin.reflect.KClass
import kotlin.reflect.KType

interface JsonCodec<BUILDER_TYPE : Any> : Codec {

    fun <T> toJson(obj: T): String

    fun <T : Any> fromJson(json: String, klass: KClass<T>): T

    fun <T> fromJson(json: String, type: Type): T

    fun builder(): BUILDER_TYPE

    fun apply(builder: BUILDER_TYPE)
}

expect annotation class SerializedName(

    /**
     * @return the desired name of the field when it is serialized or deserialized
     */
    val value: String,

    /**
     * @return the alternative names of the field when it is deserialized
     */
    val alternate: Array<String> = []
)