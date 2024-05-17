package io.tagd.arch.scopable.module

import io.tagd.arch.present.mvnp.Navigatable
import io.tagd.arch.present.mvnp.Navigator
import io.tagd.di.Scope

interface NavigatableModule : Module, Navigatable {

    val navigator: ModuleNavigator?

    override fun navigator(): Navigator<NavigatableModule>? {
        return navigator
    }

    abstract class Builder<T : NavigatableModule> : Module.Builder<T>() {

        override fun name(name: String?): Builder<T> {
            this.name = name
            return this
        }

        override fun inject(bindings: Scope.() -> Unit): Builder<T> {
            super.inject(bindings)
            return this
        }

        override fun injectBidirectionalDependents(
            injector: BidirectionalModuleDependentInjector?
        ): Builder<T> {

            super.injectBidirectionalDependents(injector)
            return this
        }
    }
}