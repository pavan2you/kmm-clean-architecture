@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx

expect class Thread {

    fun getId(): Long

    fun setName(name: String): Thread

    fun getName(): String

    fun setPriority(priority: Int): Thread

    fun getPriority(): Int

    fun nativeThread(): Any

    companion object {

        fun currentThread(): Thread
    }
}