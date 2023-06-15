package io.tagd.langx

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
