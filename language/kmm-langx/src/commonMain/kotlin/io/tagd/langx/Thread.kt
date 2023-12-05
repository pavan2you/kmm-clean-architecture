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