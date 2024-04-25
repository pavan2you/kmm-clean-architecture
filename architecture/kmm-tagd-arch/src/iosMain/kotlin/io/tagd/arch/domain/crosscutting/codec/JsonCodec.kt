@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.arch.domain.crosscutting.codec

actual annotation class SerializedName actual constructor(
    actual val value: String,
    actual val alternate: Array<String>
)