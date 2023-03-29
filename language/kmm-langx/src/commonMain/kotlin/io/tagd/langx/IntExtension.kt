package io.tagd.langx

fun Int?.isNullOrLessThanZero(): Boolean {
    return this.isNull() || this!! < 0
}

fun Int?.isNullOrLessThanEqualZero(): Boolean {
    return this.isNull() || this!! <= 0
}

fun Int?.isNullOrZero(): Boolean {
    return this.isNull() || this!! == 0
}