package io.tagd.arch.access

import io.tagd.arch.control.application
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.core.Service
import io.tagd.di.Scope
import io.tagd.di.key2

/**
 * A wrapper class which puts reference(s) into a given [Scope] and access the same later.
 */
object ReferencePool : Service {

    inline fun <reified T : Any> put(reference: T, scope: Scope = application()!!.thisScope) {
        scope.bindReference(reference)
    }

    inline fun <reified T : Any> get(scope: Scope = application()!!.thisScope): T? {
        return scope.reference(key2<ReferenceHolder<T>, T>())
    }

    override fun release() {
        //no-op
    }
}

inline fun <reified T : Any> referenceOf(scope: Scope = application()!!.thisScope): T? {
    return ReferencePool.get(scope)
}