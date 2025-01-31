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

import android.os.Bundle
import io.tagd.android.mvp.MvpActivity
import io.tagd.arch.crosscutting.async.cacheIO
import io.tagd.arch.crosscutting.async.compute
import io.tagd.arch.crosscutting.async.computeIO
import io.tagd.arch.crosscutting.async.diskIO
import io.tagd.arch.crosscutting.async.networkIO
import io.tagd.arch.crosscutting.async.present
import io.tagd.arch.present.mvp.LifeCycleAwarePresenter
import io.tagd.arch.present.mvp.PresentableView
import io.tagd.core.AsyncContext

interface UsageView : PresentableView, AsyncContext {

    fun showCallerView()
}

class UsagePresenter(view : UsageView) : LifeCycleAwarePresenter<UsageView>(view) {

    override fun onBackPressed() {
        view?.showCallerView()
    }
}

class UsageActivity : MvpActivity<UsageView, UsagePresenter>(), UsageView {

    override fun onCreatePresenter(savedInstanceState: Bundle?): UsagePresenter {
        return UsagePresenter(this)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
    }

    override fun onAwaiting() {
        super.onAwaiting()
        println("Sample : onAwaiting")
    }

    override fun onInject() {
        super.onInject()
        println("Sample : onInject")
    }

    override fun onReady() {
        super.onReady()
        println("Sample : onReady")
        setContentView(R.layout.usage_view)

        val injector = AppInjector()
        injector.setup(this)
        val usage = Usage()
        usage.use()
        usage.release()

        asyncStrategyTest()
    }

    private fun asyncStrategyTest() {
        present {
            println("from present async block " + Thread.currentThread().name + " and $it")
        }

        compute {
            println("from compute async block " + Thread.currentThread().name + " and $it")
        }

        computeIO {
            println("from compute-io async block " + Thread.currentThread().name + " and $it")
        }

        diskIO {
            println("from disk-io-1 async block " + Thread.currentThread().name + " and $it")
        }

        diskIO {
            println("from disk-io-2 async block " + Thread.currentThread().name + " and $it")
        }

        networkIO {
            println("from network-io-1 async block " + Thread.currentThread().name + " and $it")
        }

        networkIO {
            println("from network-io-2 async block " + Thread.currentThread().name + " and $it")
        }

        networkIO {
            println("from network-io-3 async block " + Thread.currentThread().name + " and $it")
            val callback = {
                println("hello call back of network-io-3")
            }
            it.notify {
                callback.invoke()
            }
        }

        cacheIO {
            println("from cache-io-1 async block " + Thread.currentThread().name + " and $it")
        }

        cacheIO {
            println("from cache-io-2 async block " + Thread.currentThread().name + " and $it")
        }

        cacheIO {
            println("from cache-io-3 async block " + Thread.currentThread().name + " and $it")
        }
    }

    override fun showCallerView() {
        finish()
    }

    override fun release() {
    }
}