@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx

actual class System {

    actual companion object {
        actual fun millis(): Long {
            return java.lang.System.currentTimeMillis()
        }

        actual fun nanos(): Long {
            return java.lang.System.nanoTime()
        }
    }
}