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

package io.tagd.arch.domain.crosscutting.codec

import io.tagd.langx.IllegalAccessException
import io.tagd.langx.Serializable
import kotlin.reflect.KClass
import kotlin.reflect.KType

abstract class JsonCodec<T : Serializable> : Codec<T, String> {

    override fun encode(plain: T, klass: KClass<String>?, type: KType?): String {
        return marshal(plain, klass, type)
    }

    abstract fun marshal(plain: T, klass: KClass<String>?, type: KType?): String

    override fun decode(encoded: String, klass: KClass<T>?, type: KType?): T {
        return unmarshal(encoded, klass, type)
    }

    fun unmarshal(marshaled: String, klass: KClass<T>?, type: KType?): T {
        return if (klass != null) {
            unmarshal(marshaled, klass)
        } else if (type != null) {
            unmarshal(marshaled, type)
        } else {
            throw IllegalAccessException()
        }
    }

    abstract fun unmarshal(obj: String, klass: KClass<T>): T

    abstract fun unmarshal(obj: String, type: KType): T
}