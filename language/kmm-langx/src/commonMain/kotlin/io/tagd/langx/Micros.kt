package io.tagd.langx

/**
 * Micros - 1/10^-6
 */
data class Micros(var micros: Long = 0L) {

    override fun toString(): String {
        return "$micros"
    }
}

@Suppress("warnings")
fun Micros?.isNull(): Boolean {
    return this == null || this.micros.isNull()
}

@Suppress("warnings")
fun Micros?.isNullOrZero(): Boolean {
    return this.isNull() || this!!.micros!!.isNullOrZero()
}

@Suppress("warnings")
fun Micros?.isNullOrLessThanZero(): Boolean {
    return this.isNull() || this!!.micros!!.isNullOrLessThanZero()
}

@Suppress("warnings")
fun Micros?.isNullOrLessThanEqualZero(): Boolean {
    return this.isNull() || this!!.micros!!.isNullOrLessThanEqualZero()
}

fun Long.micros(): Micros {
    return Micros(this)
}
fun Nanos.micros(): Micros {
    return Micros(this.nanos * 1000L)
}

fun Millis.micros(): Micros {
    return Micros(this.millis / 1000L)
}