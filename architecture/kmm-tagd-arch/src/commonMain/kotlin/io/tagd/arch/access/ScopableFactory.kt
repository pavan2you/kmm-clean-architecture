package io.tagd.arch.access

import io.tagd.arch.data.cache.Cache
import io.tagd.arch.data.dao.DataAccessObject
import io.tagd.arch.data.gateway.Gateway
import io.tagd.arch.data.repo.Repository
import io.tagd.core.CrossCutting
import io.tagd.arch.domain.service.DomainService
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.infra.InfraService
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.arch.present.service.PresentationService
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.library.Library
import io.tagd.arch.scopable.module.Module
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.Key
import io.tagd.di.Scope
import io.tagd.di.layer

fun Scopable.inject(bindings: Scope.() -> Unit): Scope {
    return io.tagd.di.scope(name, outerScope, bindings)
}

inline fun <reified T : Service, reified S : T> Scopable.bind(key: Key<S>? = null, instance: S) {
    thisScope.layer<T> {
        bind(service = key ?: io.tagd.di.key(), instance = instance)
    }
}

inline fun <reified S : Scopable> Scopable.scopable(key: Key<S>? = null): S? {
    return thisScope.scopable(key)
}

inline fun <reified S : Module> Scopable.module(key: Key<S>? = null): S? {
    return thisScope.module(key)
}

/**
 * Library Access
 */
inline fun <reified S : Library> Scopable.library(key: Key<S>? = null): S? {
    return thisScope.library(key)
}

/**
 * Infra Access
 */
inline fun <reified S : InfraService> Scopable.infraService(key: Key<S>? = null): S? {
    return thisScope.infraService(key)
}

inline fun <reified S : InfraService> Scopable.createInfra(
    key: Key<S>? = null,
    state: State? = null
): S {
    return thisScope.createInfra(key, state)
}

/**
 * Presentation Access
 */
inline fun <reified S : PresentationService> Scopable.presentationService(key: Key<S>? = null): S? {
    return thisScope.presentationService(key)
}

/**
 * Domain - Usecases Access
 */
inline fun <reified S : Command<*, *>> Scopable.usecase(key: Key<S>? = null): S? {
    return thisScope.usecase(key)
}

/**
 * Domain - Services Access
 */
inline fun <reified S : DomainService> Scopable.domainService(key: Key<S>? = null): S? {
    return thisScope.domainService(key)
}

/**
 * Data - Repositories Access
 */
inline fun <reified S : Repository> Scopable.repository(key: Key<S>? = null): S? {
    return thisScope.repository(key)
}

/**
 * Data - Gateways Access
 */
inline fun <reified S : Gateway> Scopable.gateway(key: Key<S>? = null): S? {
    return thisScope.gateway(key)
}

/**
 * Data - Daos Access
 */
inline fun <reified S : DataAccessObject> Scopable.dao(key: Key<S>? = null): S? {
    return thisScope.dao(key)
}

/**
 * Data - Cache Access
 */
inline fun <reified S : Cache<*>> Scopable.cache(key: Key<S>? = null): S? {
    return thisScope.cache(key)
}

/**
 * Vertical - CrossCutting Access
 */
inline fun <reified S : CrossCutting> Scopable.crosscutting(key: Key<S>? = null): S? {
    return thisScope.crosscutting(key)
}

/**
 * Vertical - Reference Access
 */
inline fun <T, reified S : ReferenceHolder<T>> Scopable.reference(key: Key<S>? = null): T? {
    return thisScope.reference(key)
}