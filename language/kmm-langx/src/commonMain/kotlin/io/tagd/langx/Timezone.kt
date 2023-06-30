package io.tagd.langx

expect class Timezone private constructor() {

    val rawOffset: Int

    companion object {

        fun wrap(native: Any) : Timezone

        fun default() : Timezone

        fun timezoneOf(id: String): Timezone
    }
}