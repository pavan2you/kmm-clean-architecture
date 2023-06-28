package io.tagd.langx

/**
 * Millis - 1/10^-3
 */
data class Millis(var millis: Long = 0L) {

    override fun toString(): String {
        return "$millis"
    }
}

@Suppress("warnings")
fun Millis?.isNull(): Boolean {
    return this == null || this.millis.isNull()
}

@Suppress("warnings")
fun Millis?.isNullOrZero(): Boolean {
    return this.isNull() || this!!.millis!!.isNullOrZero()
}

@Suppress("warnings")
fun Millis?.isNullOrLessThanZero(): Boolean {
    return this.isNull() || this!!.millis!!.isNullOrLessThanZero()
}

@Suppress("warnings")
fun Millis?.isNullOrLessThanEqualZero(): Boolean {
    return this.isNull() || this!!.millis!!.isNullOrLessThanEqualZero()
}

fun Long.Millis(): Millis {
    return Millis(this)
}

fun Micros.millis(): Millis {
    return Millis(this.micros * 1000L)
}

fun Nanos.millis(): Millis {
    return Millis(this.nanos * 1000 * 1000L)
}
