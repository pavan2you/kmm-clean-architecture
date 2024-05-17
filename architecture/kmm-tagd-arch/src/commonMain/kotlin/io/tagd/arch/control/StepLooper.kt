package io.tagd.arch.control

import io.tagd.arch.domain.crosscutting.async.cancelAsync
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.langx.IllegalAccessException
import io.tagd.langx.assert
import io.tagd.langx.collection.ArrayDeque
import io.tagd.langx.collection.Queue

@Suppress("LeakingThis")
abstract class StepLooper<
    S : StepLooper.Steps,
    D : StepLooperSpec.Dispatcher
>(private val steps: Steps, dispatcher: D) : StepLooperSpec<S, D> {

    abstract class Steps : StepLooperSpec.Steps {
        final override val INVALID = -1
        final override val INITIALIZING = INVALID + 1
        final override val REGISTERING = INITIALIZING + 1
        abstract override val nextStep: Int
    }

    override var dispatcher: D? = dispatcher
    protected val registry = StepRegistrar(steps.nextStep)
    private val toBeCompletedQueue: Queue<Int> = ArrayDeque()
    protected open var currentStep: Int = steps.INVALID

    init {
        dispatcher.looper = this
        registerDefaultSteps(dispatcher)
        toBeCompletedQueue.addAll(registry.registeredOrder)
    }

    protected open fun registerDefaultSteps(dispatcher: D) {
        registry.register(
            stepId = steps.INITIALIZING,
            stepLabel = "initializing",
            block = dispatcher::dispatchStepInitialize
        )
        registry.register(
            stepId = steps.REGISTERING,
            stepLabel = "registering",
            block = dispatcher::dispatchStepRegisterLoadingSteps
        )
    }

    override fun start() {
        compute {
            startInternal()
        }
    }

    /*@WorkerThread*/
    private fun startInternal() {
        if (currentStep > steps.INVALID) {
            throw IllegalAccessException("Step Registry Handler is already in progress")
        }

        dispatchHandleStep(nextStep())
    }

    override fun onRegisterStep() {
        //no-op
    }

    override fun register(stepLabel: String, block: (() -> Unit)?): Int {
        return registry.register(stepLabel, block).also {
            toBeCompletedQueue.add(it)
        }
    }

    override fun stepLabel(step: Int): String? {
        return registry.stepLabel(step)
    }

    override fun step(label: String): Int {
        return registry.step(label)
    }

    /*@WorkerThread*/
    override fun onComplete(step: Int) {
        assert(step > steps.INVALID && step < registry.size)

        toBeCompletedQueue.remove(step)
        dispatchHandleStep(nextStep())
    }

    /*@WorkerThread*/
    override fun onComplete(stepLabel: String) {
        onComplete(registry.step(stepLabel))
    }

    private fun nextStep(): Int {
        return if (toBeCompletedQueue.isEmpty()) {
            steps.INVALID
        } else {
            toBeCompletedQueue.peek() ?: steps.INVALID
        }
    }

    private fun dispatchHandleStep(step: Int) {
        if (step != this.currentStep) {
            if (step == steps.INVALID) {
                dispatcher?.dispatchLoopFinish()
            } else {
                this.currentStep = step
                onHandleStep(step)
            }
        }
    }

    override fun onHandleStep(step: Int): Boolean {
        return registry.block(step)?.let {
            it.invoke()
            true
        } ?: false
    }

    /*@WorkerThread*/
    override fun release() {
        cancelAsync()
        registry.release()
        dispatcher = null
    }
}