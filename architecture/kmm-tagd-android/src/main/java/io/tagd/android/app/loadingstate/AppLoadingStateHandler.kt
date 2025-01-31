package io.tagd.android.app.loadingstate

import io.tagd.android.app.TagdApplication
import io.tagd.arch.app.AppLoadingStateHandlerSpec
import io.tagd.arch.app.IApplication
import io.tagd.arch.app.StepLooper
import io.tagd.arch.app.StepLooperSpec

private typealias AppSteps = AppLoadingStateHandler.Steps
private typealias AppDispatcher = AppLoadingStateHandler.Dispatcher

open class AppLoadingStateHandler(dispatcher: AppLoadingStepDispatcher<out TagdApplication>) :
    StepLooper<AppSteps, AppDispatcher>(Steps, dispatcher),
    AppLoadingStateHandlerSpec<AppSteps, AppDispatcher> {

    interface Dispatcher : StepLooperSpec.Dispatcher {

        val application: TagdApplication?

        fun dispatchStepInject()

        fun dispatchStepVersionCheck()

        fun dispatchStepUpgrading()
    }

    object Steps : StepLooper.Steps() {
        val LAUNCHING = REGISTERING + 1
        val INJECTING = LAUNCHING + 1
        val VERSION_CHECK = INJECTING + 1
        val UPGRADING = VERSION_CHECK + 1
        override val nextStep: Int = UPGRADING + 1
    }

    override val scopable: IApplication?
        get() = dispatcher?.application

    override fun registerDefaultSteps(dispatcher: AppDispatcher) {
        super.registerDefaultSteps(dispatcher)
        registry.register(
            stepId = Steps.LAUNCHING,
            stepLabel = "launching",
            block = { /* no-op, taken care by OS */ }
        )
        registry.register(
            stepId = Steps.INJECTING,
            stepLabel = "injecting",
            block = dispatcher::dispatchStepInject
        )
        registry.register(
            stepId = Steps.VERSION_CHECK,
            stepLabel = "version-check",
            block = dispatcher::dispatchStepVersionCheck
        )
        registry.register(
            stepId = Steps.UPGRADING,
            stepLabel = "upgrading",
            block = dispatcher::dispatchStepUpgrading
        )
    }
}