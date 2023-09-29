package io.tagd.kotlinx.coroutines

import com.google.common.util.concurrent.ThreadFactoryBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

fun dispatcherWith(nameFormat: String): ExecutorCoroutineDispatcher {
    return Executors.newSingleThreadExecutor(
        ThreadFactoryBuilder().setNameFormat(nameFormat).build()
    ).asCoroutineDispatcher()
}

private val defaultComputationDispatcher = dispatcherWith("compute-%d")
private val defaultComputeIODispatcher = dispatcherWith("compute-io-%d")
private val defaultDaoIODispatcher = dispatcherWith("dao-io-%d")

val kotlinx.coroutines.Dispatchers.Computation: CoroutineDispatcher
    get() = defaultComputationDispatcher

val kotlinx.coroutines.Dispatchers.ComputeIO: CoroutineDispatcher
    get() = defaultComputeIODispatcher

val kotlinx.coroutines.Dispatchers.DaoIO: CoroutineDispatcher
    get() = defaultDaoIODispatcher