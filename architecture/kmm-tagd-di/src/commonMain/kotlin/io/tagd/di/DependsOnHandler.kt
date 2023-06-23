package io.tagd.di

import io.tagd.core.Releasable
import io.tagd.core.Service

internal class DependsOnHandler : Releasable {

    private val dependencyDag = hashMapOf<Key<out Service>, ArrayList<DependableService>>()
    internal var finishCallback: (() -> Unit)? = null

    fun <T : DependableService> dependsOn(
        dependent: T,
        influencers: List<Key<out Service>>
    ) {

        val available = hashMapOf<Key<out Service>, Service>()

        influencers.forEach { influencer ->
            val found = Global.get(influencer)
            if (found == null) {
                var dependents = dependencyDag[influencer]
                if (dependents == null) {
                    dependents = arrayListOf()
                    dependencyDag[influencer] = dependents
                }
                if (!dependents.contains(dependent)) {
                    dependents.add(dependent)
                }
            } else {
                available[influencer] = found
            }
        }

        if (available.isNotEmpty()) {
            if (dependencyDag.isNotEmpty()) {
                available.forEach {
                    notifyDependents(it.key, it.value)
                }
            } else {
                dispatchDagFinish()
            }
        }
    }

    fun notifyDependents(key: Key<out Service>, instance: Service) {
        if (dependencyDag.containsKey(key)) {
            val dependents = dependencyDag[key]
            dependents?.forEach {
                it.onDependencyAvailable(key, instance)
            }
            dependents?.clear()
            dependencyDag.remove(key)
            if (dependencyDag.isEmpty()) {
                dispatchDagFinish()
            }
        }
    }

    private fun dispatchDagFinish() {
        finishCallback?.invoke()
    }

    override fun toString(): String {
        return "dependsOn - $dependencyDag, callback - $finishCallback"
    }

    override fun release() {
        dependencyDag.clear()
        finishCallback = null
    }
}