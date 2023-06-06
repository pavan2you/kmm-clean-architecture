package io.tagd.android.mvnp

import io.tagd.arch.present.mvnp.Navigator
import java.lang.ref.WeakReference

open class NavigatableActivityNavigator<N : NavigatableActivity<*, *, *>>(navigatable: N) :
    Navigator<N> {

    private var navigatableReference: WeakReference<N>? = WeakReference(navigatable)

    override val navigatable: N?
        get() = navigatableReference?.get()

    override fun release() {
        navigatableReference?.clear()
        navigatableReference = null
    }
}