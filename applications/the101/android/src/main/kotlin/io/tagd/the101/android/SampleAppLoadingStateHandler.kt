package io.tagd.the101.android

import io.tagd.android.app.AppLoadingStateHandler
import io.tagd.android.app.TagdApplication
import io.tagd.arch.access.library
import io.tagd.arch.access.usecase
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.arch.domain.crosscutting.async.present
import io.tagd.arch.library.usecase

class SampleAppLoadingStateHandler(application: TagdApplication) :
    AppLoadingStateHandler(application) {

    override fun onRegisterStep() {
        super.onRegisterStep()
        register(APP_SPECIFIC_LOADING_STATE_HEAVY_BG_WORK)
    }

    override fun onHandleStep(step: Int): Boolean {
        var handled = super.onHandleStep(step)

        if (!handled) {

            when (stepLabel(step)) {
                APP_SPECIFIC_LOADING_STATE_HEAVY_BG_WORK -> {
                    doHeavyLongBackgroundWork(step)
                    handled = true
                }
            }
        }

        return handled
    }

    private fun doHeavyLongBackgroundWork(state: Int) {
        library<SampleLibrary>()?.let {
            val usecase = it.usecase<LibraryUsecase>()
            println("access usecase within library scope $usecase")
            println("access usecase through global scope ${usecase<LibraryUsecase>()}")
            usecase?.execute()
        }

        compute (delay = 3000L) {
            present {
                onComplete(state)
            }
        }
    }

    private companion object {
        private const val APP_SPECIFIC_LOADING_STATE_HEAVY_BG_WORK = "heavy_background_work"
    }
}