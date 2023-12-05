package io.tagd.langx

expect class Thread {

    fun getId(): Long

    fun setName(name: String): Thread

    fun getName(): String

    fun setPriority(priority: Int): Thread

    fun getPriority(): Int

    companion object {

        fun currentThread(): Thread
    }
}