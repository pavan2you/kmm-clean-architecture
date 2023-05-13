package io.tagd.arch.module

import io.tagd.arch.present.mvnp.Navigatable
import io.tagd.arch.present.mvnp.Navigator
import io.tagd.langx.Context
import io.tagd.langx.Intent
import kotlin.reflect.KClass

abstract class ModuleNavigator(val moduleName: String) : Navigator<Module> {

    override var navigatable: Navigatable?
        get() = TODO("Not yet implemented")
        set(value) {}

    fun <T : Navigatable> navigator() {
    }

    fun putDecorNavigator(kclass: KClass<out Navigatable>, navigator: Navigator<out Navigatable>) {
        //todo
    }

    abstract fun launch(caller: Navigatable)

    abstract fun navigateTo(caller: Navigatable, pathWithArgs: String)

    abstract fun navigateTo(caller: Navigatable, intent: Intent)

    abstract fun navigateImplicitlyTo(caller: Navigatable, action: String)

    abstract fun navigateTo(caller: Navigatable, kclass: KClass<out Navigatable>)
}