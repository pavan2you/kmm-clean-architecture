package io.tagd.di

import io.tagd.core.Releasable
import io.tagd.core.Service

internal class DependsOnHandler : Releasable {

    private val dependencyDag = DependencyDag()
    internal var finishCallback: (() -> Unit)? = null

    internal fun <T : DependableService> dependsOn(
        dependent: T,
        influencers: List<Key<out Service>>
    ) {

        influencers.forEach { influencer ->
            dependencyDag.put(influencer, Global.name, dependent)
            notifyDependents(Global, influencer)
        }
    }

    internal fun <T : DependableService> dependsOn(
        dependent: T,
        scopedInfluencers: () -> List<Pair<String, Key<out Service>>>
    ) {

        scopedInfluencers.invoke().forEach { influencerFromScope ->
            val influencerScope = influencerFromScope.first
            val influencer = influencerFromScope.second
            dependencyDag.put(influencer, influencerScope, dependent)
            val scope = Global.getSubScope(influencerScope)
            scope?.let {
                notifyDependents(scope, influencer)
            }
        }
    }

    private fun notifyDependents(scope: Scope, influencer: Key<out Service>) {
        val foundWithScope = scope.getWithScope(influencer)
        if (foundWithScope.second != null) {
            notifyDependents(foundWithScope.first!!, influencer, foundWithScope.second!!)
        }
    }

    internal fun notifyDependents(scope: Scope, key: Key<out Service>, instance: Service) {
        if (dependencyDag.containsKey(key)) {
            val dependents = dependencyDag[key]?.get(scope.name)
            dependents?.forEach {
                it.onDependencyAvailable(scope, key, instance)
            }
            dependents?.clear()
            dependencyDag.remove(key, scope.name)
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