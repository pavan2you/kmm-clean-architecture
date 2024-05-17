package io.tagd.arch.control

import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.arch.infra.InfraService

interface StepLooperSpec<S : StepLooperSpec.Steps, D : StepLooperSpec.Dispatcher> : InfraService,
    AsyncContext {

    @Suppress("PropertyName")
    interface Steps {
        val INVALID: Int
        val INITIALIZING: Int
        val REGISTERING: Int
        val nextStep: Int
    }

    interface Notifier {

        var looper: StepLooperSpec<*, *>?

        /*@WorkerThread*/
        fun dispatchStepComplete(step: Int) {
            looper?.onComplete(step)
        }

        /*@WorkerThread*/
        fun dispatchStepComplete(stepLabel: String) {
            looper?.onComplete(stepLabel)
        }
    }

    interface Dispatcher : Notifier {

        fun dispatchStepInitialize()

        fun dispatchStepRegisterLoadingSteps()

        fun dispatchLoopFinish()
    }

    var dispatcher: D?

    fun onRegisterStep()

    fun register(stepLabel: String, block : (() -> Unit)? = null): Int

    fun stepLabel(step: Int): String?

    fun step(label: String): Int

    fun onHandleStep(step: Int): Boolean

    /**
     * Call this through [StepLooperSpec.Notifier.dispatchStepComplete]
     */
    fun onComplete(step: Int)

    /**
     * Call this through [StepLooperSpec.Notifier.dispatchStepComplete]
     */
    fun onComplete(stepLabel: String)

    fun start()
}