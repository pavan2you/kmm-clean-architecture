@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx

expect class System {

    companion object {
        fun millis(): Long

        fun nanos() : Long
    }
}