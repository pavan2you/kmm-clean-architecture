package io.tagd.arch.domain.crosscutting.codec

actual annotation class SerializedName actual constructor(
    actual val value: String,
    actual val alternate: Array<String>
)