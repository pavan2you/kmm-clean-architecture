package io.tagd.di

import io.tagd.core.Releasable
import io.tagd.core.Service

interface DependableService : Service, Releasable {

    enum class State {
        INITIALIZING, AWAITING, READY, DIRTY
    }

    val dependsOnServices: ArrayList<Key<out Service>>
    val dependencyAvailableCallbacks: HashMap<Key<out Service>, (service: Service) -> Unit>
    var state: State

    fun registerCallback(key: Key<out Service>, callback: (service: Service) -> Unit) {
        dependencyAvailableCallbacks[key] = callback
    }

    fun dependsOn(services: List<Key<out Service>>) {
        state = State.AWAITING
        dependsOnServices.clear()
        dependsOnServices.addAll(services)

        Global.dependsOn(this, services)
    }

    fun onDependencyAvailable(key: Key<out Service>, service: Service) {
        dependsOnServices.remove(key)
        dependencyAvailableCallbacks[key]?.invoke(service)
        if (dependsOnServices.isEmpty()) {
            state = State.READY
            onReady()
        }
    }

    fun onReady() {
        //no-op
    }

    fun initializing() = state === State.INITIALIZING

    fun awaiting() = state === State.AWAITING

    fun ready() = state === State.READY

    fun dirty() = state === State.DIRTY

    override fun release() {
        state = State.DIRTY
        dependsOnServices.clear()
        dependencyAvailableCallbacks.clear()
    }
}