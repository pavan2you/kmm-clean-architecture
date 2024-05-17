package io.tagd.arch.scopable.module

import io.tagd.arch.present.mvnp.Navigatable
import io.tagd.arch.present.mvnp.Navigator

abstract class ModuleNavigator(val module: NavigatableModule) : Navigator<NavigatableModule> {

    override var navigatable: Navigatable? = module

}