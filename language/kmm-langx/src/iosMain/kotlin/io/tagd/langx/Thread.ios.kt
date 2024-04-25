@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx

actual class Thread {

    actual fun getId(): Long {
        TODO("Not yet implemented")
    }

    actual fun setName(name: String): Thread {
        TODO("Not yet implemented")
    }

    actual fun getName(): String {
        TODO("Not yet implemented")
    }

    actual fun setPriority(priority: Int): Thread {
        TODO("Not yet implemented")
    }

    actual fun getPriority(): Int {
        TODO("Not yet implemented")
    }

    actual fun nativeThread(): Any {
        TODO("Not yet implemented")
    }

    actual companion object {
        actual fun currentThread(): Thread {
            TODO("Not yet implemented")
        }

    }
}