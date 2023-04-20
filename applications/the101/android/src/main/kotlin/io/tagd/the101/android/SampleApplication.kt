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

package io.tagd.the101.android

import android.os.Handler
import android.os.Looper
import io.tagd.android.app.Injector
import io.tagd.android.app.TagdApplication
import io.tagd.arch.access.library
import io.tagd.arch.access.usecase
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.library.Library
import io.tagd.arch.library.inject
import io.tagd.arch.library.usecase
import io.tagd.di.Global
import io.tagd.di.key
import io.tagd.di.layer

class SampleApplication : TagdApplication() {

    override fun initInjector(): Injector {
        return MyInjector(this)
    }

    override fun onLoading() {
        library<SampleLibrary>()?.let {
            val usecase = it.usecase<LibraryUsecase>()
            println("access usecase within library scope $usecase")
            println("access usecase through global scope ${usecase<LibraryUsecase>()}")
            usecase?.execute()
        }
        Handler(Looper.getMainLooper()).postDelayed({
            dispatchOnLoadingComplete()
        }, 5000)
    }

    class MyInjector(application: TagdApplication) : Injector(application) {

        override fun inject() {
            super.inject()
            with(Global) {
                layer<Library> {
                    bind(key(), initSampleLibrary())
                }
                println()
            }
        }

        private fun initSampleLibrary(): SampleLibrary {
            return SampleLibrary.Builder()
                .name("sample").
                inject {
                    println("inside library specific injection")
                    layer<Command<*, *>> {
                        bind(key(), LibraryUsecase())
                    }
                }.injectBidirectionalDependents {
                    println("inside bidirectional dependents")
                }
                .build()
        }
    }
}