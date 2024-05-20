package io.tagd.the101.android

import io.tagd.arch.access.library
import io.tagd.arch.control.LoadingStateHandler
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.langx.Callback
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.ScopableInitializer
import io.tagd.arch.scopable.library.AbstractLibrary
import io.tagd.arch.scopable.library.Library
import io.tagd.core.Dependencies
import io.tagd.core.dependencies
import io.tagd.di.Scope
import io.tagd.di.layer

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

class SampleLibraryInitializer : ScopableInitializer<SampleInitializerBackedLibrary> {

    override var outer: Scopable? = null

    override fun initialize(
        outer: Scopable,
        callback: Callback<Unit>
    ) {

        this.outer = outer

        with(outer.thisScope) {
            layer<Library> {
                bind<SampleInitializerBackedLibrary>().toLazy {
                    new(dependencies(
                        "NAME" to "sample-initializer-backed-library",
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
        handler.register("SAMPLE_LIB_GLOBAL_WORK") {
            compute(delay = 2000) {
                handler.dispatcher?.dispatchStepComplete("SAMPLE_LIB_GLOBAL_WORK")
            }
        }
        callback.invoke(Unit)
    }

    override fun inject(callback: Callback<Unit>) {
        with(outer!!.library<SampleInitializerBackedLibrary>()?.thisScope!!) {
            layer<Command<*, *>> {
                bind<LibraryUsecase>().toInstance(LibraryUsecase())
            }
        }
        callback.invoke(Unit)
    }

    override fun new(dependencies: Dependencies): SampleInitializerBackedLibrary {
        return SampleInitializerBackedLibrary.Builder()
            .name("sample-initializer-backed-library")
            .scope(outer!!.thisScope)
            .build()
    }

    override fun release() {
        outer = null
        super.release()
    }
}