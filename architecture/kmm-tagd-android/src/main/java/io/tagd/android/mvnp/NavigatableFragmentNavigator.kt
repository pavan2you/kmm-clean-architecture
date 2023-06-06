package io.tagd.android.mvnp

import io.tagd.arch.present.mvnp.Navigator
import java.lang.ref.WeakReference

open class NavigatableFragmentNavigator<N : NavigatableFragment<*, *, *>>(navigatable: N) :
    Navigator<N> {

    private var weakNavigatable: WeakReference<N>? = WeakReference(navigatable)

    override val navigatable: N?
        get() = weakNavigatable?.get()

    override fun release() {
        weakNavigatable?.clear()
        weakNavigatable = null
    }
}