package io.tagd.arch.module

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
import io.tagd.arch.access.scope
import io.tagd.arch.access.usecase
import io.tagd.arch.data.cache.Cache
import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.arch.domain.crosscutting.CrossCutting
import io.tagd.arch.domain.service.DomainService
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.infra.InfraService
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.arch.library.Library
import io.tagd.arch.present.service.PresentationService
import io.tagd.core.Factory
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.DependentService
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.Scopable
import io.tagd.di.Scope
import io.tagd.di.layer
import io.tagd.di.scope

typealias BidirectionalModuleDependentInjector = (context: Module) -> Unit

interface Module : Factory, Scopable {

    val outerScope
        get() = scope

    val outerScopeName
        get() = scope.name

    abstract class Builder<T : Module> : Factory.Builder<T>() {

        protected var outerScope: Scope = Global
        private var bidirectionalInjector: BidirectionalModuleDependentInjector? = null
        private var injectionInvoker: InjectionInvoker? = null

        override fun name(name: String?): Builder<T> {
            this.name = name
            return this
        }

        open fun scope(outer: Scope? = Global): Builder<T> {
            this.outerScope = outer ?: Global
            return this
        }

        open fun inject(bindings: Scope.() -> Unit): Builder<T> {
            this.injectionInvoker = InjectionInvoker(outerScope, bindings)
            return this
        }

        open fun injectBidirectionalDependents(
            injector: BidirectionalModuleDependentInjector?
        ): Builder<T> {

            this.bidirectionalInjector = injector
            return this
        }

        override fun build(): T {
            return buildModule().also { module ->
                inject(context = module)
            }
        }

        protected abstract fun buildModule(): T

        protected open fun inject(context: Module) {
            injectionInvoker?.invoke(context)
            bidirectionalInjector?.invoke(context)
        }

        protected open class InjectionInvoker(
            private val parent: Scope? = Global,
            private val bindings: Scope.() -> Unit
        ) {

            open operator fun invoke(context: Module): Scope {
                return context.inject(parent, bindings)
            }
        }
    }
}

abstract class AbstractModule(final override val name: String, final override val scope: Scope) :
    Module {

    init {
        scope.addSubScopeIfAbsent(Scope(name))
    }

    override fun release() {
        scope.removeSubScope(name)
    }
}

abstract class AbstractDependentModule(
    name: String,
    scope: Scope
) : AbstractModule(name, scope), DependentService {

    override val dependencyAvailableCallbacks:
            HashMap<Key<out Service>, (service: Service) -> Unit> = hashMapOf()

    override val dependsOnServices: ArrayList<Key<out Service>> = arrayListOf()

    override var state: DependentService.State = DependentService.State.INITIALIZING

    override fun release() {
        super<DependentService>.release()
        super<AbstractModule>.release()
    }
}

fun Module.inject(parent: Scope? = Global, bindings: Scope.() -> Unit): Scope {
    return scope(name, parent, bindings)
}

inline fun <reified T : Service, reified S : T> Module.bind(key: Key<S>? = null, instance: S) {
    scope(name, outerScope)?.layer<T> {
        bind(service = key ?: io.tagd.di.key(), instance = instance)
    }
}

inline fun <reified S : Module> Module.module(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.module(key)
}

/**
 * Library Access
 */
inline fun <reified S : Library> Module.library(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.library(key)
}

/**
 * Infra Access
 */
inline fun <reified S : InfraService> Module.infraService(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.infraService(key)
}

inline fun <reified S : InfraService> Module.createInfra(
    key: Key<S>? = null,
    state: State? = null
): S? {
    return scope(name, outerScope)?.createInfra(key, state)
}

/**
 * Presentation Access
 */
inline fun <reified S : PresentationService> Module.presentationService(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.presentationService(key)
}

/**
 * Domain - Usecases Access
 */
inline fun <reified S : Command<*, *>> Module.usecase(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.usecase(key)
}

/**
 * Domain - Services Access
 */
inline fun <reified S : DomainService> Module.domainService(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.domainService(key)
}

/**
 * Data - Repositories Access
 */
inline fun <reified S : Repository> Module.repository(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.repository(key)
}

/**
 * Data - Gateways Access
 */
inline fun <reified S : Gateway> Module.gateway(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.gateway(key)
}

/**
 * Data - Daos Access
 */
inline fun <reified S : DataAccessObject> Module.dao(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.dao(key)
}

/**
 * Data - Cache Access
 */
inline fun <reified S : Cache<*>> Module.cache(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.cache(key)
}

/**
 * Vertical - CrossCutting Access
 */
inline fun <reified S : CrossCutting> Module.crosscutting(key: Key<S>? = null): S? {
    return scope(name, outerScope)?.crosscutting(key)
}

/**
 * Vertical - Reference Access
 */
inline fun <T, reified S : ReferenceHolder<T>> Module.reference(key: Key<S>? = null): T? {
    return scope(name, outerScope)?.reference(key)
}