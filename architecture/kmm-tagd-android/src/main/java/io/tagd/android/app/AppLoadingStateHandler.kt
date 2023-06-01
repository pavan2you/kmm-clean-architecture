package io.tagd.android.app

import androidx.annotation.MainThread
import io.tagd.arch.domain.crosscutting.async.AsyncContext
import io.tagd.core.Releasable
import io.tagd.core.Service
import java.lang.ref.WeakReference
import java.util.LinkedList
import java.util.Queue

open class AppLoadingStateHandler(application: TagdApplication) : Service, AsyncContext {

    object States {
        const val INVALID = -1
        const val INITIALIZING = 0
        const val INJECTING = 1
        const val LAUNCHING = 2
        internal const val VERSION_CHECK = 3
        const val UPGRADING = 4
    }

    internal class StateRegistry(initialNextId: Int) : Releasable {

        private var nextStateRegistryId = initialNextId
        private val stateRegistry = LinkedHashMap<Int, String>()
        val insertedOrder = ArrayList<Int>()

        fun register(stateLabel: String): Int {
            val stateId = nextStateRegistryId++
            return register(stateId, stateLabel)
        }

        fun register(stateId: Int, stateLabel: String): Int {
            stateRegistry[stateId] = stateLabel
            insertedOrder.add(stateId)
            return stateId
        }

        fun stateLabel(state: Int): String? {
            return stateRegistry[state]
        }

        override fun release() {
            stateRegistry.clear()
        }
    }

    private var weakApplication: WeakReference<out TagdApplication>? = WeakReference(application)

    private val application
        get() = weakApplication?.get()

    private val registry = StateRegistry(States.UPGRADING + 1)

    private val toBeCompletedQueue : Queue<Int> = LinkedList()

    private var currentState: Int = States.INVALID

    private var hasVersionChange: Boolean = false

    @MainThread
    fun start() {
        if (currentState > States.INITIALIZING) {
            throw IllegalAccessException("Loading is already in progress")
        }

        currentState = States.INITIALIZING
        dispatchRegisterState()
        toBeCompletedQueue.addAll(registry.insertedOrder)
        dispatchHandleState(nextState())
    }

    private fun dispatchRegisterState() {
        onRegisterState()
    }

    protected open fun onRegisterState() {
        registry.register(States.INJECTING, "injecting")
        registry.register(States.LAUNCHING, "launching")
        registry.register(States.VERSION_CHECK, "version-check")
        registry.register(States.UPGRADING, "upgrading")
    }

    fun register(stateLabel: String): Int {
        return registry.register(stateLabel)
    }

    fun stateLabel(state: Int): String? {
        return registry.stateLabel(state)
    }

    @MainThread
    fun onComplete(state: Int) {
        toBeCompletedQueue.remove(state)
        dispatchHandleState(nextState())
    }

    private fun nextState(): Int {
        return if (toBeCompletedQueue.isEmpty()) {
            -1
        } else {
            toBeCompletedQueue.peek() ?: -1
        }
    }

    private fun dispatchHandleState(state: Int) {
        if (state != this.currentState) {
            if (state == States.INVALID) {
                dispatchLoadingDone()
            } else {
                this.currentState = state
                onHandleState(state)
            }
        }
    }

    private fun dispatchLoadingDone() {
        application?.dispatchReady()
    }

    @MainThread
    protected open fun onHandleState(state: Int): Boolean {
        return when (state) {
            States.INJECTING -> {
                application?.dispatchInject()
                true
            }
            States.LAUNCHING -> {
                /* no-op, taken care by OS */
                true
            }
            States.VERSION_CHECK -> {
                detectIfThereIsAnyVersionChange()
                true
            }
            States.UPGRADING -> {
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
        onComplete(States.VERSION_CHECK)
    }

    private fun handleUpgradeIfAny() {
        if (hasVersionChange) {
            application?.dispatchUpgrade()
        } else {
            onComplete(States.UPGRADING)
        }
    }

    override fun release() {
        registry.release()
    }
}