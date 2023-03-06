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