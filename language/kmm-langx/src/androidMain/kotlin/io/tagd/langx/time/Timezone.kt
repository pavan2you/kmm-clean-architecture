@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.time

import java.util.TimeZone

actual class Timezone private actual constructor() {

    lateinit var delegate: TimeZone
        private set

    actual val rawOffset: Int
        get() = delegate.rawOffset

    actual companion object {

        actual fun wrap(native: Any) : Timezone {
            return Timezone().apply {
                delegate = native as TimeZone
            }
        }

        actual fun default() : Timezone {
            return Timezone().apply {
                delegate = TimeZone.getDefault()
            }
        }

        actual fun timezoneOf(id: String): Timezone {
            return Timezone().apply {
                delegate = TimeZone.getTimeZone(id)
            }
        }
    }
}