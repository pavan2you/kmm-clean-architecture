package io.tagd.di

import io.tagd.core.Service

object Global : Scope() {

    fun <T : DependentService> dependsOn(
        dependent: T,
        influencers: List<Key<out Service>>
    ) {
        DependencyHandler.dependsOn(dependent, influencers)
    }

    override fun reset() {
        super.reset()
        DependencyHandler.reset()
    }

    override fun release() {
        DependencyHandler.release()
        super.release()
    }
}