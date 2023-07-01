package io.tagd.arch.present.mvp

import io.tagd.core.Factory
import kotlin.reflect.KClass

class PresenterFactory(override val name: String) : Factory {

    val presenters =
        hashMapOf<KClass<out PresentableView>, Presenter<out PresentableView>>()

    fun put(view: KClass<out PresentableView>, presenter: Presenter<out PresentableView>) {
        presenters[view] = presenter
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified V : PresentableView> get(view: KClass<out V>): Presenter<V>? {
        return presenters[view] as Presenter<V>?
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified V : PresentableView, P : Presenter<out V>> getOrNew(
        view: KClass<out V>,
        new: () -> P
    ): Presenter<out V> {

        return (presenters[view] ?: new.invoke().apply {
            presenters[view] = this
        }) as Presenter<out V>
    }

    fun clear(view: KClass<out PresentableView>) {
        presenters[view]?.let {
            it.onDestroy()
            presenters.remove(view)
        }
    }

    override fun release() {
        presenters.forEach { entry ->
            val presenter = entry.value
            presenter.onDestroy()
        }
    }
}