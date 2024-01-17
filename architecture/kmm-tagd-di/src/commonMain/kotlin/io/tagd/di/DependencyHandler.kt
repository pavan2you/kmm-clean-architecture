package io.tagd.di

import io.tagd.core.Service

internal object DependencyHandler : Service {

    private var dependsOnHandler: DependsOnHandler? = DependsOnHandler()

    var dependsOnFinishCallback: (() -> Unit)? = null
        set(value) {
            field = value
            dependsOnHandler?.finishCallback = value
        }

    internal fun <T : DependableService> dependsOn(
        dependent: T,
        influencers: List<Key<out Service>>
    ) {
        dependsOnHandler?.dependsOn(dependent, influencers)
    }

    internal fun <S : Service> notifyDependents(scope: Scope, key: Key<S>, instance: S) {
        dependsOnHandler?.notifyDependents(scope, key, instance)
    }

    internal fun reset() {
        dependsOnHandler?.release()
        dependsOnHandler = DependsOnHandler()
        dependsOnHandler?.finishCallback = dependsOnFinishCallback
    }

    override fun release() {
        dependsOnFinishCallback = null
        dependsOnHandler?.release()
        dependsOnHandler = null
    }
}