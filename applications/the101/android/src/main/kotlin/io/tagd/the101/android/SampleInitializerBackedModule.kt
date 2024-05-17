package io.tagd.the101.android

import io.tagd.arch.control.LoadingStateHandler
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.arch.domain.usecase.Callback
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.ScopableInitializer
import io.tagd.arch.scopable.module.AbstractModule
import io.tagd.arch.scopable.module.Module
import io.tagd.core.Dependencies
import io.tagd.core.dependencies
import io.tagd.di.Scope
import io.tagd.di.layer

class SampleInitializerBackedModule private constructor(
    name: String,
    outerScope: Scope,
) : AbstractModule(name, outerScope) {

    class Builder : Module.Builder<SampleInitializerBackedModule>() {

        override fun name(name: String?): Builder {
            super.name(name)
            return this
        }

        override fun scope(outer: Scope?): Builder {
            super.scope(outer)
            return this
        }

        override fun buildModule(): SampleInitializerBackedModule {
            return SampleInitializerBackedModule(name!!, outerScope)
        }
    }
}

class SampleModuleInitializer : ScopableInitializer<SampleInitializerBackedModule> {

    override var outer: Scopable? = null

    override fun initialize(
        outer: Scopable,
        callback: Callback<Unit>
    ) {

        this.outer = outer

        with(outer.thisScope) {
            layer<Module> {
                bind<SampleInitializerBackedModule>().toLazy {
                    new(dependencies(
                        "NAME" to "sample-initializer-backed-module",
                        "OUTER_SCOPE" to this
                    ).apply {
                        it?.let {
                            putAll(it)
                        }
                    })
                }
            }
        }

        callback.invoke(Unit)
    }

    override fun <Outer : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<Outer, *, *>,
        callback: Callback<Unit>
    ) {
        handler.register("SAMPLE_MODULE_GLOBAL_WORK") {
            compute(delay = 2000) {
                handler.dispatcher?.dispatchStepComplete("SAMPLE_LIB_GLOBAL_WORK")
            }
        }
    }

    override fun inject(callback: Callback<Unit>) {
        //nothing
        callback.invoke(Unit)
    }

    override fun new(dependencies: Dependencies): SampleInitializerBackedModule {
        return SampleInitializerBackedModule.Builder()
            .name("sample-initializer-backed-module")
            .scope(outer!!.thisScope)
            .build()
    }

    override fun release() {
        outer = null
        super.release()
    }
}