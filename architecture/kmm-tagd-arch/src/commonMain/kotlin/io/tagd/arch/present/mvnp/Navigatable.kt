package io.tagd.arch.present.mvnp

import io.tagd.langx.Intent
import kotlin.reflect.KClass

interface Navigatable : NavigateToService {

    fun <N : Navigatable> navigator(): Navigator<out N>?

    override fun navigateTo(pathWithArgs: String, builder: NavigateToBuilder?) {
        navigator<Navigatable>()?.navigateTo(pathWithArgs)
    }

    override fun navigateTo(intent: Intent) {
        navigator<Navigatable>()?.navigateTo(intent)
    }

    override fun navigateImplicitlyTo(action: String, builder: NavigateToBuilder?) {
        navigator<Navigatable>()?.navigateTo(action)
    }

    override fun navigateTo(kclass: KClass<out Navigatable>, builder: NavigateToBuilder?) {
        navigator<Navigatable>()?.navigateTo(kclass)
    }
}