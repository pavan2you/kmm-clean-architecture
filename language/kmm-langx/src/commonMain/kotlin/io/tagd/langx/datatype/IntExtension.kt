package io.tagd.langx.datatype

import io.tagd.langx.isNull

fun Int?.isNullOrLessThanZero(): Boolean {
    return this.isNull() || this!! < 0
}

fun Int?.isNullOrLessThanEqualZero(): Boolean {
    return this.isNull() || this!! <= 0
}

fun Int?.isNullOrZero(): Boolean {
    return this.isNull() || this!! == 0
}