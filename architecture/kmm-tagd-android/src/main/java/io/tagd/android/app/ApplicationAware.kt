package io.tagd.android.app

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.arch.domain.crosscutting.async.cancelAsync
import java.lang.ref.WeakReference

open class ApplicationAware(application : TagdApplication) : AsyncContext {

    private var weakApplication: WeakReference<TagdApplication?>? = WeakReference(application)

    val application: TagdApplication?
        get() = weakApplication?.get()

    override fun release() {
        cancelAsync()
        weakApplication?.clear()
        weakApplication = null
    }
}