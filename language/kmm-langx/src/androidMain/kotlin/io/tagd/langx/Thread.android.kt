package io.tagd.langx

actual class Thread(private var jvmThread: java.lang.Thread) {

    actual fun setName(name: String): Thread {
        jvmThread.name = name
        return this
    }

    actual fun getName(): String {
        return jvmThread.name
    }

    actual fun setPriority(priority: Int): Thread {
        jvmThread.priority = priority
        return this
    }

    actual fun getPriority(): Int {
        return jvmThread.priority
    }

    actual fun getId(): Long {
        return jvmThread.id
    }

    actual companion object {

        actual fun currentThread(): Thread {
            return Thread(java.lang.Thread.currentThread())
        }
    }
}