@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.time

expect class Timezone private constructor() {

    val rawOffset: Int

    companion object {

        fun wrap(native: Any) : Timezone

        fun default() : Timezone

        fun timezoneOf(id: String): Timezone
    }
}