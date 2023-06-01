package io.tagd.android.app

import androidx.annotation.MainThread
import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.core.Releasable
import io.tagd.core.Service
import java.lang.ref.WeakReference
import java.util.LinkedList
import java.util.Queue

open class AppLoadingStateHandler(application: TagdApplication) : Service, AsyncContext {

    object Steps {
        internal const val INVALID = -1
        internal const val INITIALIZING = 0
        const val INJECTING = 1
        const val LAUNCHING = 2
        internal const val VERSION_CHECK = 3
        const val UPGRADING = 4
    }

    internal class StepRegistry(initialNextId: Int) : Releasable {

        private var nextStepRegistryId = initialNextId
        private val registry = LinkedHashMap<Int, String>()
        private val reverseRegistry = LinkedHashMap<String, Int>()
        val insertedOrder = ArrayList<Int>()

        val size: Int
            get() = nextStepRegistryId

        fun register(stepLabel: String): Int {
            val stepId = nextStepRegistryId++
            return register(stepId, stepLabel)
        }

        fun register(stepId: Int, stepLabel: String): Int {
            registry[stepId] = stepLabel
            reverseRegistry[stepLabel] = stepId
            insertedOrder.add(stepId)
            return stepId
        }

        fun stepLabel(step: Int): String? {
            return registry[step]
        }

        fun step(label: String): Int {
            return reverseRegistry[label] ?: -1
        }

        override fun release() {
            registry.clear()
        }
    }

    private var weakApplication: WeakReference<out TagdApplication>? = WeakReference(application)

    private val application
        get() = weakApplication?.get()

    private val registry = StepRegistry(Steps.UPGRADING + 1)

    private val toBeCompletedQueue : Queue<Int> = LinkedList()

    private var currentStep: Int = Steps.INVALID

    private var hasVersionChange: Boolean = false

    @MainThread
    fun start() {
        if (currentStep > Steps.INITIALIZING) {
            throw IllegalAccessException("Loading is already in progress")
        }

        currentStep = Steps.INITIALIZING
        dispatchRegisterStep()
        toBeCompletedQueue.addAll(registry.insertedOrder)
        dispatchHandleStep(nextStep())
    }

    private fun dispatchRegisterStep() {
        onRegisterStep()
    }

    protected open fun onRegisterStep() {
        registry.register(Steps.INJECTING, "injecting")
        registry.register(Steps.LAUNCHING, "launching")
        registry.register(Steps.VERSION_CHECK, "version-check")
        registry.register(Steps.UPGRADING, "upgrading")
    }

    fun register(stepLabel: String): Int {
        return registry.register(stepLabel)
    }

    fun stepLabel(step: Int): String? {
        return registry.stepLabel(step)
    }

    fun step(label: String): Int {
        return registry.step(label)
    }

    @MainThread
    fun onComplete(step: Int) {
        assert(step > Steps.INITIALIZING && step < registry.size)

        toBeCompletedQueue.remove(step)
        dispatchHandleStep(nextStep())
    }

    private fun nextStep(): Int {
        return if (toBeCompletedQueue.isEmpty()) {
            -1
        } else {
            toBeCompletedQueue.peek() ?: -1
        }
    }

    private fun dispatchHandleStep(step: Int) {
        if (step != this.currentStep) {
            if (step == Steps.INVALID) {
                dispatchLoadingDone()
            } else {
                this.currentStep = step
                onHandleStep(step)
            }
        }
    }

    private fun dispatchLoadingDone() {
        application?.dispatchReady()
    }

    @MainThread
    protected open fun onHandleStep(step: Int): Boolean {
        return when (step) {
            Steps.INJECTING -> {
                application?.dispatchInject()
                true
            }
            Steps.LAUNCHING -> {
                /* no-op, taken care by OS */
                true
            }
            Steps.VERSION_CHECK -> {
                detectIfThereIsAnyVersionChange()
                true
            }
            Steps.UPGRADING -> {
                handleUpgradeIfAny()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun detectIfThereIsAnyVersionChange() {
        if (application?.versionTracker()?.isVersionChange() == true) {
            hasVersionChange = true
        }
        onComplete(Steps.VERSION_CHECK)
    }

    private fun handleUpgradeIfAny() {
        if (hasVersionChange) {
            application?.dispatchUpgrade()
        } else {
            onComplete(Steps.UPGRADING)
        }
    }

    override fun release() {
        registry.release()
    }
}