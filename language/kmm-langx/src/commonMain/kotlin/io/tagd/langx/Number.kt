package io.tagd.langx

fun Boolean.toLong(): Long {
    return if (this) {
        1L
    } else {
        0L
    }
}

fun Long.toBoolean(): Boolean {
    return this > 0L
}