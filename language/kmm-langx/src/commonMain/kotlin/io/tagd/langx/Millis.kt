package io.tagd.langx

data class Millis(var millis: Long = 0L)

@Suppress("warnings")
fun Millis?.isNull(): Boolean {
    return this == null || this.millis == null
}

@Suppress("warnings")
fun Millis?.isNullOrZero(): Boolean {
    return this.isNull() || this?.millis == 0L
}