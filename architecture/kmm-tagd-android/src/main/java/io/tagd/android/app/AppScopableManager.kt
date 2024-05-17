package io.tagd.android.app

import io.tagd.arch.control.LoadingStateHandler
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.ScopableInitializer
import io.tagd.arch.scopable.ScopableManager

open class AppScopableManager : ScopableManager() {

    override fun loadScopableInitializers(initializers: ArrayList<ScopableInitializer<*>>) {
        //no-op
    }

    override fun <Outer : Scopable> canRegisterLoadingSteps(
        handler: LoadingStateHandler<Outer, *, *>
    ): Boolean {

        return handler.scopable === outer
    }
}