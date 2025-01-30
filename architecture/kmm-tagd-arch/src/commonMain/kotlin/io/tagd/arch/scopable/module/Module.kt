package io.tagd.arch.scopable.module

import io.tagd.arch.access.cache
import io.tagd.arch.access.createInfra
import io.tagd.arch.access.crosscutting
import io.tagd.arch.access.dao
import io.tagd.arch.access.domainService
import io.tagd.arch.access.gateway
import io.tagd.arch.access.infraService
import io.tagd.arch.access.library
import io.tagd.arch.access.module
import io.tagd.arch.access.presentationService
import io.tagd.arch.access.reference
import io.tagd.arch.access.repository
import io.tagd.arch.access.usecase
import io.tagd.arch.data.cache.Cache
import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.core.CrossCutting
import io.tagd.core.AsyncContext
import io.tagd.arch.domain.crosscutting.async.compute
import io.tagd.arch.domain.service.DomainService
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.infra.InfraService
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.arch.present.service.PresentationService
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.library.Library
import io.tagd.core.Factory
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.DependentService
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.Scope
import io.tagd.di.layer
import io.tagd.di.scope
import io.tagd.langx.Callback

interface Module : Factory, Scopable {

    val outerScopeName
        get() = outerScope.name

    val thisScopeName
        get() = thisScope.name
    
    abstract class Builder<T : Module> : Factory.Builder<T>() {

        protected open var outerScope: Scope = Global
        private var bindings: (Scope.(T) -> Unit)? = null

        override fun name(name: String?): Builder<T> {
            this.name = name
            return this
        }

        open fun scope(outer: Scope? = Global): Builder<T> {
            this.outerScope = outer ?: Global
            return this
        }

        open fun inject(bindings: Scope.(T) -> Unit): Builder<T> {
            this.bindings = bindings
            return this
        }

        override fun build(): T {
            return buildModule().also { module ->
                inject(context = module)
            }
        }

        fun build(context: AsyncContext, callback: Callback<T>) {
            context.compute {
                val module = build()
                it.notify {
                    callback.invoke(module)
                }
            }
        }

        protected abstract fun buildModule(): T

        protected open fun inject(context: T) {
            bindings?.invoke(context.thisScope, context)
        }
    }
}

abstract class AbstractModule(
    final override val name: String,
    final override val outerScope: Scope
) : Module {

    final override val thisScope: Scope = outerScope.addSubScopeIfAbsent(name)

    override fun release() {
        outerScope.removeSubScope(name)
    }
}

abstract class AbstractDependentModule(
    name: String,
    outerScope: Scope
) : AbstractModule(name, outerScope), DependentService {

    override val dependencyAvailableCallbacks:
            HashMap<Key<out Service>, (service: Service) -> Unit> = hashMapOf()

    override val dependsOnServices: ArrayList<Key<out Service>> = arrayListOf()

    override var state: DependentService.State = DependentService.State.INITIALIZING

    override fun release() {
        super<DependentService>.release()
        super<AbstractModule>.release()
    }
}

fun Module.inject(bindings: Scope.() -> Unit): Scope {
    return scope(name, outerScope, bindings)
}

inline fun <reified T : Service, reified S : T> Module.bind(key: Key<S>? = null, instance: S) {
    thisScope.layer<T> {
        bind(service = key ?: io.tagd.di.key(), instance = instance)
    }
}

inline fun <reified S : Module> Module.module(key: Key<S>? = null): S? {
    return thisScope.module(key)
}

/**
 * Library Access
 */
inline fun <reified S : Library> Module.library(key: Key<S>? = null): S? {
    return thisScope.library(key)
}

/**
 * Infra Access
 */
inline fun <reified S : InfraService> Module.infraService(key: Key<S>? = null): S? {
    return thisScope.infraService(key)
}

inline fun <reified S : InfraService> Module.createInfra(
    key: Key<S>? = null,
    state: State? = null
): S {
    return thisScope.createInfra(key, state)
}

/**
 * Presentation Access
 */
inline fun <reified S : PresentationService> Module.presentationService(key: Key<S>? = null): S? {
    return thisScope.presentationService(key)
}

/**
 * Domain - Usecases Access
 */
inline fun <reified S : Command<*, *>> Module.usecase(key: Key<S>? = null): S? {
    return thisScope.usecase(key)
}

/**
 * Domain - Services Access
 */
inline fun <reified S : DomainService> Module.domainService(key: Key<S>? = null): S? {
    return thisScope.domainService(key)
}

/**
 * Data - Repositories Access
 */
inline fun <reified S : Repository> Module.repository(key: Key<S>? = null): S? {
    return thisScope.repository(key)
}

/**
 * Data - Gateways Access
 */
inline fun <reified S : Gateway> Module.gateway(key: Key<S>? = null): S? {
    return thisScope.gateway(key)
}

/**
 * Data - Daos Access
 */
inline fun <reified S : DataAccessObject> Module.dao(key: Key<S>? = null): S? {
    return thisScope.dao(key)
}

/**
 * Data - Cache Access
 */
inline fun <reified S : Cache<*>> Module.cache(key: Key<S>? = null): S? {
    return thisScope.cache(key)
}

/**
 * Vertical - CrossCutting Access
 */
inline fun <reified S : CrossCutting> Module.crosscutting(key: Key<S>? = null): S? {
    return thisScope.crosscutting(key)
}

/**
 * Vertical - Reference Access
 */
inline fun <T, reified S : ReferenceHolder<T>> Module.reference(key: Key<S>? = null): T? {
    return thisScope.reference(key)
}