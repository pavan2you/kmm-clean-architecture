package io.tagd.langx.time

import io.tagd.langx.System

data class UnixEpochInMillis(val millisSince1970: Millis = Millis(millis = System.millis())) {

    override fun toString(): String {
        return "$millisSince1970"
    }
}