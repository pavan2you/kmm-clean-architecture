package io.tagd.the101.android

import io.tagd.arch.access.library
import io.tagd.arch.app.LoadingStateHandler
import io.tagd.arch.crosscutting.async.compute
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.scopable.AbstractWithinScopableInitializer
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.bindLazy
import io.tagd.arch.scopable.library.AbstractLibrary
import io.tagd.arch.scopable.library.Library
import io.tagd.core.Dependencies
import io.tagd.core.dependencies
import io.tagd.di.Scope
import io.tagd.di.layer
import io.tagd.langx.Callback

class SampleInitializerBackedLibrary private constructor(
    name: String,
    outerScope: Scope,
) : AbstractLibrary(name, outerScope) {

    class Builder : Library.Builder<SampleInitializerBackedLibrary>() {

        override fun name(name: String?): Builder {
            super.name(name)
            return this
        }

        override fun scope(outer: Scope?): Builder {
            super.scope(outer)
            return this
        }

        override fun buildLibrary(): SampleInitializerBackedLibrary {
            return SampleInitializerBackedLibrary(name!!, outerScope)
        }
    }
}

class SampleLibraryInitializer<S : Scopable>(within: S) :
    AbstractWithinScopableInitializer<S, SampleInitializerBackedLibrary>(within) {

    override fun initialize(callback: Callback<Unit>) {
        within.bindLazy<Library, SampleInitializerBackedLibrary> {
            new(dependencies(
                "NAME" to "sample-initializer-backed-library",
                "OUTER_SCOPE" to this
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
        handler.register("SAMPLE_LIB_GLOBAL_WORK") {
            compute(delay = 2000) {
                handler.dispatcher?.dispatchStepComplete("SAMPLE_LIB_GLOBAL_WORK")
            }
        }
        callback.invoke(Unit)
    }

    override fun inject(callback: Callback<Unit>) {
        val thisScope = within.library<SampleInitializerBackedLibrary>()?.thisScope!!

        with(thisScope) {
            layer<Command<*, *>> {
                bind<LibraryUsecase>().toInstance(LibraryUsecase())
            }
        }
        callback.invoke(Unit)
    }

    override fun new(dependencies: Dependencies): SampleInitializerBackedLibrary {
        return SampleInitializerBackedLibrary.Builder()
            .name("sample-initializer-backed-library")
            .scope(outerScope)
            .build()
    }
}