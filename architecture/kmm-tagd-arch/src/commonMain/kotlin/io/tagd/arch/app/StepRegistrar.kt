package io.tagd.arch.app

import io.tagd.core.Releasable

class StepRegistrar(initialNextId: Int) : Releasable {

    private var nextStepRegistryId = initialNextId
    private val registry = LinkedHashMap<Int, String>()
    private val reverseRegistry = LinkedHashMap<String, Int>()
    private val blockRegistry = HashMap<Int, (() -> Unit)?>()
    val registeredOrder = ArrayList<Int>()

    val size: Int
        get() = nextStepRegistryId

    fun register(stepLabel: String, block: (() -> Unit)?): Int {
        val stepId = nextStepRegistryId++
        return register(stepId, stepLabel, block)
    }

    fun register(stepId: Int, stepLabel: String, block: (() -> Unit)? = null): Int {
        registry[stepId] = stepLabel
        reverseRegistry[stepLabel] = stepId
        registeredOrder.add(stepId)
        blockRegistry[stepId] = block
        return stepId
    }

    fun stepLabel(step: Int): String? {
        return registry[step]
    }

    fun step(label: String): Int {
        return reverseRegistry[label] ?: -1
    }

    fun block(step: Int): (() -> Unit)? {
        return blockRegistry[step]
    }

    override fun release() {
        registry.clear()
        reverseRegistry.clear()
        blockRegistry.clear()
        registeredOrder.clear()
    }
}