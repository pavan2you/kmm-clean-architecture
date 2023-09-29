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

package io.tagd.android.crosscutting.async

import androidx.annotation.VisibleForTesting
import io.tagd.arch.domain.crosscutting.async.*
import io.tagd.kotlinx.coroutines.Dispatchers
import io.tagd.langx.ref.WeakReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.CoroutineContext

open class CoroutineStrategy(
    protected val coroutineContext: CoroutineContext = Dispatchers.Main,
    override val exceptionHandler: AsyncExceptionHandler? = null
) : AsyncStrategy {

    private val job = Job()
    private val scope: CoroutineScope = CoroutineScope(job + coroutineContext)

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val spannedJobs = ConcurrentHashMap<WeakReference<out Any>, CopyOnWriteArrayList<Job>>()

    override fun execute(context: AsyncContext?, delay: Long, work: () -> Unit) {
        executeCoroutine(context, delay, work)
    }

    protected open fun executeCoroutine(context: AsyncContext?, delayMs: Long, work: () -> Unit) {
        val job = scope.launch {
            try {
                if (delayMs > 0) {
                    delay(delayMs)
                }
                work.invoke()
            } catch (e: Exception) {
                handleCoroutineException(e)
            }
        }
        monitorJob(context, job)
    }

    private fun handleCoroutineException(e: Exception) {
        e.printStackTrace()
        if (e.cause == null) {
            exceptionHandler?.asyncException(
                IgnoredCoroutineException(
                    message = "ignorable",
                    cause = e
                )
            )
        } else {
            exceptionHandler?.asyncException(e)
        }
    }

    private fun monitorJob(context: AsyncContext?, job: Job) {
        val key = WeakReference(context ?: "global")
        var contextJobs = spannedJobs[key]
        if (contextJobs == null) {
            contextJobs = CopyOnWriteArrayList()
            spannedJobs[key] = contextJobs
        }
        contextJobs.add(job)

        job.invokeOnCompletion {
            contextJobs.remove(job)
        }
    }

    override fun cancel(context: Any?): Boolean {
        val key = WeakReference(context ?: "global")
        val contextJobs = spannedJobs[key]
        contextJobs?.let {
            var i = 0
            // Using while loop to fix ConcurrentModificationException
            while (i < contextJobs.size) {
                val job = it[i]
                if (job.isActive) {
                    job.cancel()
                }
                i++
            }
        }
        contextJobs?.clear()
        return true
    }

    override fun release() {
        spannedJobs.keys.forEach {
            cancel(it)
        }
        spannedJobs.clear()

        job.cancel()
    }
}

class CoroutinePresentationStrategy(exceptionHandler: AsyncExceptionHandler? = null) :
    CoroutineStrategy(
        coroutineContext = Dispatchers.Main,
        exceptionHandler = exceptionHandler
    ), PresentationStrategy

class CoroutineComputationStrategy(exceptionHandler: AsyncExceptionHandler? = null) :
    CoroutineStrategy(
        coroutineContext = Dispatchers.Computation,
        exceptionHandler = exceptionHandler
    ), ComputationStrategy

class CoroutineComputeIOStrategy(exceptionHandler: AsyncExceptionHandler? = null) :
    CoroutineStrategy(
        coroutineContext = Dispatchers.ComputeIO,
        exceptionHandler = exceptionHandler
    ), ComputeIOStrategy

class CoroutineNetworkStrategy(exceptionHandler: AsyncExceptionHandler? = null) :
    CoroutineStrategy(
        coroutineContext = Dispatchers.IO,
        exceptionHandler = exceptionHandler
    ), NetworkIOStrategy

class CoroutineDiskStrategy(exceptionHandler: AsyncExceptionHandler? = null) :
    CoroutineStrategy(
        coroutineContext = Dispatchers.IO,
        exceptionHandler = exceptionHandler
    ), DiskIOStrategy

class CoroutineDaoIOStrategy(exceptionHandler: AsyncExceptionHandler? = null) :
    CoroutineStrategy(
        coroutineContext = Dispatchers.DaoIO,
        exceptionHandler = exceptionHandler
    ), DaoIOStrategy

class CoroutineCacheIOStrategy(exceptionHandler: AsyncExceptionHandler? = null) :
    CoroutineStrategy(
        coroutineContext = Dispatchers.IO,
        exceptionHandler = exceptionHandler
    ), CacheIOStrategy

class IgnoredCoroutineException(message: String?, cause: Throwable) : Exception(message, cause)