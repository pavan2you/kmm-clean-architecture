package io.tagd.langx

fun Long?.isNullOrLessThanZero(): Boolean {
    return this.isNull() || this!! < 0L
}

fun Long?.isNullOrLessThanEqualZero(): Boolean {
    return this.isNull() || this!! <= 0L
}

fun Long?.isNullOrZero(): Boolean {
    return this.isNull() || this!! == 0L
}