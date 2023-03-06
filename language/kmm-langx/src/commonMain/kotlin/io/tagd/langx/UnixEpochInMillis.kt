package io.tagd.langx

data class UnixEpochInMillis(val millisSince1970: Millis = Millis(millis = System.millis()))