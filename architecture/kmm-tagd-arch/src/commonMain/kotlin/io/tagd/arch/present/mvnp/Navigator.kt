package io.tagd.arch.present.mvnp

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.core.Service
import io.tagd.langx.Intent
import kotlin.reflect.KClass

interface NavigateToBuilder {

    fun appendTo(intent: Intent)
}

interface NavigateToService : Service, AsyncContext {

    fun navigateTo(intent: Intent)

    fun navigateTo(pathWithArgs: String, builder: NavigateToBuilder? = null)

    fun navigateImplicitlyTo(action: String, builder: NavigateToBuilder? = null)

    fun navigateTo(kclass: KClass<out Navigatable>, builder: NavigateToBuilder? = null)
}

interface Navigator<out N : Navigatable> : NavigateToService {

    val navigatable: Navigatable?

    override fun navigateTo(intent: Intent) {
        //no-op
    }

    override fun navigateTo(pathWithArgs: String, builder: NavigateToBuilder?) {
        //no-op
    }

    override fun navigateTo(kclass: KClass<out Navigatable>, builder: NavigateToBuilder?) {
        //no-op
    }

    override fun navigateImplicitlyTo(action: String, builder: NavigateToBuilder?) {
        //no-op
    }
}