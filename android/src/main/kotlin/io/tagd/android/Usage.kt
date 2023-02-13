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

package io.tagd.app

import android.content.Context
import io.tagd.android.InfraService
import io.tagd.android.SimpleDao
import io.tagd.android.SimpleGateway
import io.tagd.android.SimpleRepo
import io.tagd.android.SimpleRepo2
import io.tagd.android.SimpleTypedService
import io.tagd.android.SomeObject
import io.tagd.core.Releasable
import io.tagd.di.inject
import io.tagd.di.injectX
import io.tagd.di.key
import io.tagd.di.key2

class Usage : Releasable {

    private val simpleGateway by inject<SimpleGateway>()
    private val simpleRepo by inject<SimpleRepo>(scope = "application")
    private val simpleRepo2 by inject<SimpleRepo2>(scope = "application")
    private val simpleDao by inject<SimpleDao>(scope = "random")

    private val infraService by inject<InfraService<Context>>()

    private val typedContextService by inject(
        key = key2<SimpleTypedService<Context>, Context>()
    )

    private val typeSomeObjectService by inject(
        key = key2<SimpleTypedService<SomeObject>, SomeObject>()
    )

    private var simpleRepo2Obj2 by injectX<SimpleRepo2>(
        key = key("Repo2Obj2")
    )
    private var simpleRepo2Obj3 by injectX<SimpleRepo2>(
        key = key("Repo2Obj3")
    )

    fun use() {
        println("Gateway -> $simpleGateway")
        println("Repo -> $simpleRepo")
        println("Repo2 -> $simpleRepo2")
        println("Dao -> $simpleDao")
        println("Infra -> $infraService")
        println("typedOne -> $typedContextService")
        println("typedTwo -> $typeSomeObjectService")
        println("Repo2Obj2 -> $simpleRepo2Obj2")
        println("Repo2Obj3 -> $simpleRepo2Obj3")
    }

    override fun release() {
        simpleRepo2Obj2 = null
        simpleRepo2Obj3 = null
    }
}