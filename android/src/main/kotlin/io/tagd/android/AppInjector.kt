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

import android.content.Context
import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.di.*

class AppInjector {

    fun setup(context: Context) {
        scope("application") {

            //Infra layer
            layer<Infra> {
                bind(key(), InfraService(context))
            }

            //Infra mix layer
            layer<TypedService<*>> {
                bind(key2<SimpleTypedService<Context>, Context>(),
                    SimpleTypedService()
                )
                bind(
                    key<SimpleTypedService<SomeObject>>(
                        typeOf<SomeObject>()
                    ),
                    SimpleTypedService()
                )
            }

            //Repo layer
            layer<Repository> {
                bind<SimpleRepo>().toInstance(
                    SimpleRepo()
                )
                bind<SimpleRepo2>().toInstance(
                    SimpleRepo2()
                )
                bind(key("Repo2Obj2"), SimpleRepo2())
                bind(key("Repo2Obj3"), SimpleRepo2())
            }

            //Dao layer
            layer<DataAccessObject> {
                bind<SimpleDao>().toInstance(
                    SimpleDao()
                )
            }

            //Gateway layer
            layer<Gateway> {
                bind<SimpleGateway>().toInstance(
                    SimpleGateway()
                )
            }
        }
    }
}