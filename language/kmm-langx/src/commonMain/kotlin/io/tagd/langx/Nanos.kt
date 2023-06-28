package io.tagd.langx

/**
 * Nanos - 1/10^-9
 */
data class Nanos(var nanos: Long = 0L) {

    override fun toString(): String {
        return "$nanos"
    }
}

@Suppress("warnings")
fun Nanos?.isNull(): Boolean {
    return this == null || this.nanos.isNull()
}

@Suppress("warnings")
fun Nanos?.isNullOrZero(): Boolean {
    return this.isNull() || this!!.nanos!!.isNullOrZero()
}

@Suppress("warnings")
fun Nanos?.isNullOrLessThanZero(): Boolean {
    return this.isNull() || this!!.nanos!!.isNullOrLessThanZero()
}

@Suppress("warnings")
fun Nanos?.isNullOrLessThanEqualZero(): Boolean {
    return this.isNull() || this!!.nanos!!.isNullOrLessThanEqualZero()
}

fun Long.nanos(): Nanos {
    return Nanos(this)
}

fun Micros.nanos(): Nanos {
    return Nanos(this.micros / 1000L)
}

fun Millis.nanos(): Nanos {
    return Nanos(this.millis / (1000 * 1000L))
}
