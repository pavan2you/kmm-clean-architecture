package io.tagd.the101.android

import io.tagd.android.app.AppLoadingStateHandler
import io.tagd.android.app.Injector
import io.tagd.android.app.TagdApplication
import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.arch.domain.crosscutting.async.present
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.library.Library
import io.tagd.di.Global
import io.tagd.di.key
import io.tagd.di.layer

class SampleAppInjector(application: TagdApplication) : Injector(application), AsyncContext {

    override fun inject() {
        super.inject()
        with(Global) {
            layer<Library> {
                bind(key(), initSampleLibrary())
            }
        }
        injectAsync()
    }

    private fun initSampleLibrary(): SampleLibrary {
        return SampleLibrary.Builder()
            .name("sample").
            inject {
                println("inside library specific injection")
                layer<Command<*, *>> {
                    bind(key(), LibraryUsecase())
                }
            }.injectBidirectionalDependents {
                println("inside bidirectional dependents")
            }
            .build()
    }

    private fun injectAsync() {
        compute(delay = 1000L) {
            present {
                app?.dispatchOnLoadingStateStepDone(AppLoadingStateHandler.States.INJECTING)
            }
        }
    }
}