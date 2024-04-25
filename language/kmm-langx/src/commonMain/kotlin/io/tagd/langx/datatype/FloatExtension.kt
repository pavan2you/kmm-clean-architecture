package io.tagd.langx.datatype

import io.tagd.langx.isNull

fun Float?.isNullOrLessThanZero(): Boolean {
    return this.isNull() || this!! < 0.0f
}

fun Float?.isNullOrLessThanEqualZero(): Boolean {
    return this.isNull() || this!! <= 0.0f
}

fun Float?.isNullOrZero(): Boolean {
    return this.isNull() || this!! == 0.0f
}