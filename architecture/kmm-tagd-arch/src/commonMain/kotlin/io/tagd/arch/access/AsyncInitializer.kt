package io.tagd.arch.access

import io.tagd.core.AsyncContext
import io.tagd.arch.crosscutting.async.cancelAsync
import io.tagd.arch.crosscutting.async.compute
import io.tagd.core.Dependencies
import io.tagd.core.Initializer
import io.tagd.langx.Callback

interface AsyncInitializer<T> : Initializer<T>, AsyncContext {

    fun new(dependencies: Dependencies, callback: Callback<T>) {
        compute {
            val instance = new(dependencies)
            it.notify {
                callback.invoke(instance)
            }
        }
    }

    interface Builder<T, I : AsyncInitializer<T>> : Initializer.Builder<T, I>, AsyncContext {

        fun build(callback: Callback<I>) {
            compute {
                val instance = build()
                it.notify {
                    callback.invoke(instance)
                }
            }
        }
    }

    override fun release() {
        cancelAsync()
        super.release()
    }
}