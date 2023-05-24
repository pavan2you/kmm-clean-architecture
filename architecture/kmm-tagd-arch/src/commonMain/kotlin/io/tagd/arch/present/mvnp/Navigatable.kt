package io.tagd.arch.present.mvnp

import io.tagd.langx.Intent
import kotlin.reflect.KClass

interface Navigatable : NavigateToService {

    fun navigator(): Navigator<Navigatable>?

    override fun navigateTo(pathWithArgs: String, builder: NavigateToBuilder?) {
        navigator()?.navigateTo(pathWithArgs)
    }

    override fun navigateTo(intent: Intent) {
        navigator()?.navigateTo(intent)
    }

    override fun navigateImplicitlyTo(action: String, builder: NavigateToBuilder?) {
        navigator()?.navigateTo(action)
    }

    override fun navigateTo(kclass: KClass<out Navigatable>, builder: NavigateToBuilder?) {
        navigator()?.navigateTo(kclass)
    }
}