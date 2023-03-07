package io.tagd.core

fun assert(condition: Boolean, message: String? = null) {
    if (!condition) {
        throw AssertionError(message)
    }
}

fun <T> assertNull(value: T?, message: String? = null) {
    if (value != null) {
        throw AssertionError(message ?: "value should be null")
    }
}

fun <T> assertNotNull(value: T?, message: String? = null) {
    if (value == null) {
        throw AssertionError(message ?: "value is null")
    }
}