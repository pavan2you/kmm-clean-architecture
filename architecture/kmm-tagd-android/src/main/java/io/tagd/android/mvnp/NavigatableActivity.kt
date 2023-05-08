package io.tagd.android.mvnp

import android.os.Bundle
import io.tagd.android.mvp.MvpActivity
import io.tagd.arch.present.mvnp.Navigatable
import io.tagd.arch.present.mvnp.NavigatablePresenter
import io.tagd.arch.present.mvnp.NavigatableView
import io.tagd.arch.present.mvnp.Navigator

abstract class NavigatableActivity<
    V : NavigatableView,
    N : Navigator<V>,
    P : NavigatablePresenter<V, N>
> : MvpActivity<V, P>(), NavigatableView {

    protected var navigator: N? = null

    override fun interceptOnCreate(savedInstanceState: Bundle?) {
        onCreateNavigator(savedInstanceState)
        super.interceptOnCreate(savedInstanceState)
    }

    protected abstract fun onCreateNavigator(savedInstanceState: Bundle?): N

    @Suppress("UNCHECKED_CAST")
    override fun <N : Navigatable> navigator(): Navigator<N>? = navigator as? Navigator<N>

    override fun release() {
        navigator?.release()
        navigator = null
        super.release()
    }
}