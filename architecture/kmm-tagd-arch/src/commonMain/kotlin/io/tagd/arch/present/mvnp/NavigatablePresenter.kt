package io.tagd.arch.present.mvnp

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.arch.present.mvp.LifeCycleAwarePresenter
import io.tagd.arch.present.mvp.Presenter
import io.tagd.langx.ref.WeakReference

interface NavigatablePresenter<V : NavigatableView, N : Navigator<V>> : Presenter<V> {

    val navigator: N?
}

open class NavigatableLifeCycleAwarePresenter<V : NavigatableView, N : Navigator<V>>(
    view: V,
    navigator: N
) : LifeCycleAwarePresenter<V>(view = view), NavigatablePresenter<V, N>, AsyncContext {

    private var weakNavigator: WeakReference<N>? = WeakReference(navigator)

    override val navigator: N?
        get() = weakNavigator?.get()
}