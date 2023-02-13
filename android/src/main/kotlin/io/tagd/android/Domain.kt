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

package io.tagd.android

import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.core.Service
import java.lang.ref.WeakReference

interface Infra : Service
interface TypedService<T> : Service

class SimpleGateway : Gateway {
    override fun release() {
    }
}

class SimpleDao : DataAccessObject {
    override fun release() {
    }
}

class SimpleRepo : Repository {
    override fun release() {
    }
}

class SimpleRepo2 : Repository {
    override fun release() {
    }
}

class InfraService<T>(infra: T) : Infra {

    private var infraReference = WeakReference(infra)

    override fun release() {
        infraReference.clear()
    }

    override fun toString(): String {
        return "InfraService(infraObject=${infraReference.get()})"
    }
}

class SimpleTypedService<T> : TypedService<T> {
    override fun release() {
    }
}

class SomeObject