package io.tagd.langx

expect inline fun <R> synchronized(lock: Any, block: () -> R): R