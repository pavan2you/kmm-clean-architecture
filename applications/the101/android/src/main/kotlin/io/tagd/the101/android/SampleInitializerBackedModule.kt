package io.tagd.the101.android

import io.tagd.arch.access.module
import io.tagd.arch.app.LoadingStateHandler
import io.tagd.arch.crosscutting.async.compute
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.scopable.AbstractWithinScopableInitializer
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.bindLazy
import io.tagd.arch.scopable.module.AbstractModule
import io.tagd.arch.scopable.module.Module
import io.tagd.core.Dependencies
import io.tagd.core.dependencies
import io.tagd.di.Scope
import io.tagd.di.layer
import io.tagd.langx.Callback

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

class SampleModuleInitializer<S : Scopable>(within: S) :
    AbstractWithinScopableInitializer<S, SampleInitializerBackedModule>(within) {

    override fun initialize(callback: Callback<Unit>) {
        within.bindLazy<Module, SampleInitializerBackedModule> {
            new(dependencies(
                "NAME" to "sample-initializer-backed-module",
                "OUTER_SCOPE" to outerScope
            ).apply {
                it?.let {
                    putAll(it)
                }
            })
        }

        callback.invoke(Unit)
    }

    override fun <WITHIN : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<WITHIN, *, *>,
        callback: Callback<Unit>
    ) {
        handler.register("SAMPLE_MODULE_GLOBAL_WORK") {
            compute(delay = 2000) {
                handler.dispatcher?.dispatchStepComplete("SAMPLE_MODULE_GLOBAL_WORK")
            }
        }
        callback.invoke(Unit)
    }

    override fun inject(callback: Callback<Unit>) {
        val thisScope = within.module<SampleInitializerBackedModule>()?.thisScope!!

        with(thisScope) {
            layer<Command<*, *>> {
                bind<LibraryUsecase>().toInstance(LibraryUsecase())
            }
        }
        callback.invoke(Unit)
    }

    override fun new(dependencies: Dependencies): SampleInitializerBackedModule {
        return SampleInitializerBackedModule.Builder()
            .name("sample-initializer-backed-module")
            .scope(outerScope)
            .build()
    }
}