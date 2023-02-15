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

package io.tagd.javax.lang.ref

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * An extension of WeakReference that implements equals and hashcode
 * method based on the referent.
 *
 * @param <T> The type of object that this reference contains
 */
class ComparableWeakReference<T>(referent: T) : WeakReference<T>(referent) {

    override fun equals(other: Any?): Boolean {
        return if (other is Reference<*>) {
            other.get() == get()
        } else {
            get() == other
        }
    }

    override fun hashCode(): Int {
        return get()?.hashCode() ?: kotlin.run { 0 }
    }
}