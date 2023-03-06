package io.tagd.langx

expect class System {

    companion object {
        fun millis(): Long

        fun nanos() : Long
    }
}