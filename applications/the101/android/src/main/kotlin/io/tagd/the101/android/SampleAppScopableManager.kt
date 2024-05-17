package io.tagd.the101.android

import io.tagd.android.app.AppScopableManager
import io.tagd.arch.scopable.ScopableInitializer

class SampleAppScopableManager : AppScopableManager() {

    override fun loadScopableInitializers(initializers: ArrayList<ScopableInitializer<*>>) {
        super.loadScopableInitializers(initializers)
        initializers.add(SampleLibraryInitializer())
        initializers.add(SampleProductInitializerAndScopableManager())
    }
}