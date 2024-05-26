package io.tagd.android.app.loadingstate

import io.tagd.android.app.ApplicationAware
import io.tagd.android.app.TagdApplication
import io.tagd.arch.control.StepLooperSpec
import io.tagd.arch.domain.crosscutting.async.present
import io.tagd.kotlinx.let

open class AppLoadingStepDispatcher<T : TagdApplication>(application: T) :
    ApplicationAware<T>(application), AppLoadingStateHandler.Dispatcher {

    override var looper: StepLooperSpec<*, *>? = null

    private var hasVersionChange: Boolean = false

    override fun dispatchStepInitialize() {
        let(
            one = application?.injector,
            finally = { dispatchStepComplete(AppLoadingStateHandler.Steps.INITIALIZING) }
        ) { injector, finally ->
            injector.initialize(finally)
        }
    }

    override fun dispatchStepRegisterLoadingSteps() {
        let(
            one = application?.injector,
            finally = { dispatchStepComplete(AppLoadingStateHandler.Steps.REGISTERING) }
        ) { injector, finally ->
            injector.registerLoadingSteps(looper as AppLoadingStateHandler, finally)
        }
    }

    /**
     * The [step] value must be one of [AppLoadingStateHandler.Steps] or the derived
     * [AppLoadingStateHandler]'s steps.
     */
    override fun dispatchStepComplete(step: Int) {
        if (step == AppLoadingStateHandler.Steps.INJECTING) {
            application?.setupVersionTracker()
        }
        looper?.onComplete(step)
    }

    override fun dispatchStepInject() {
        application?.dispatchInject()
    }

    override fun dispatchStepVersionCheck() {
        detectIfThereIsAnyVersionChange()
    }

    private fun detectIfThereIsAnyVersionChange() {
        if (application?.versionTracker()?.isVersionChange() == true) {
            hasVersionChange = true
        }
        looper?.onComplete(AppLoadingStateHandler.Steps.VERSION_CHECK)
    }

    override fun dispatchStepUpgrading() {
        handleUpgradeIfAny()
    }

    private fun handleUpgradeIfAny() {
        if (hasVersionChange) {
            application?.dispatchUpgrade()
        } else {
            looper?.onComplete(AppLoadingStateHandler.Steps.UPGRADING)
        }
    }

    override fun dispatchLoopFinish() {
        present {
            application?.dispatchReady()
        }
    }

    override fun release() {
        super.release()
        looper = null
    }
}