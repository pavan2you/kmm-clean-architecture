package io.tagd.android.app

import io.tagd.core.AsyncContext
import io.tagd.arch.crosscutting.async.cancelAsync
import java.lang.ref.WeakReference

open class ApplicationAware<T : TagdApplication>(application : T) : AsyncContext {

    private var weakApplication: WeakReference<T?>? = WeakReference(application)

    val application: T?
        get() = weakApplication?.get()

    override fun release() {
        cancelAsync()
        weakApplication?.clear()
        weakApplication = null
    }
}