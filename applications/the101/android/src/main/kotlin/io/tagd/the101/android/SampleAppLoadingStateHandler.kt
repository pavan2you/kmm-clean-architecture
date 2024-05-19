package io.tagd.the101.android

import io.tagd.android.app.TagdApplication
import io.tagd.android.app.loadingstate.AppLoadingStateHandler
import io.tagd.android.app.loadingstate.AppLoadingStepDispatcher
import io.tagd.arch.access.library
import io.tagd.arch.access.usecase
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.arch.scopable.library.usecase

class SampleAppLoadingStateHandler(dispatcher: AppLoadingStepDispatcher<out TagdApplication>) :
    AppLoadingStateHandler(dispatcher) {

    override fun onRegisterStep() {
        super.onRegisterStep()
        register(APP_SPECIFIC_LOADING_STATE_HEAVY_BG_WORK, this::doHeavyLongBackgroundWork)
    }

    private fun doHeavyLongBackgroundWork() {
        library<SampleLibrary>()?.let {
            val usecase = it.usecase<LibraryUsecase>()
            println("access usecase within library scope $usecase")
            println("access usecase through global scope ${usecase<LibraryUsecase>()}")
            usecase?.execute()
        }

        compute (delay = 3000L) {
            onComplete(APP_SPECIFIC_LOADING_STATE_HEAVY_BG_WORK)
        }
    }

    private companion object {
        private const val APP_SPECIFIC_LOADING_STATE_HEAVY_BG_WORK = "heavy_background_work"
    }
}