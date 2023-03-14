package io.tagd.arch.module

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
import io.tagd.core.Nameable
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.Layer
import io.tagd.di.Scope
import io.tagd.di.create
import io.tagd.di.get
import io.tagd.di.layer

interface Module : Service, Nameable {

    abstract class Builder<T : Module> {

        abstract fun build(): T
    }
}

inline fun <reified T : Service> Module.inject(
    name: String,
    bindings: Layer<T>.() -> Unit
): Layer<T> {

    return Scope(name).locator.layer(bindings)
}

inline fun <reified S : Module> Module.moduleService(key: Key<S>? = null): S? {
    return Scope(name).get<Module, S>(key ?: io.tagd.di.key())
}

/**
 * Library Access
 */
inline fun <reified S : Library> Module.libraryService(key: Key<S>? = null): S? {
    return Scope(name).get<Library, S>(key ?: io.tagd.di.key())
}

/**
 * Infra Access
 */
inline fun <reified S : InfraService> Module.infraService(key: Key<S>? = null): S? {
    return Scope(name).get<InfraService, S>(key ?: io.tagd.di.key())
}

inline fun <reified S : InfraService> Module.createInfra(
    key: Key<S>? = null,
    state: State? = null
): S {
    return Scope(name).create(key ?: io.tagd.di.key(), state)
}

/**
 * Presentation Access
 */
inline fun <reified S : PresentationService> Module.presentationService(key: Key<S>? = null): S? {
    return Scope(name).get<PresentationService, S>(key ?: io.tagd.di.key())
}

/**
 * Domain - Usecases Access
 */
inline fun <reified S : Command<*, *>> Module.usecase(key: Key<S>? = null): S? {
    return Scope(name).get<Command<*, *>, S>(key ?: io.tagd.di.key())
}

/**
 * Domain - Services Access
 */
inline fun <reified S : DomainService> Module.domainService(key: Key<S>? = null): S? {
    return Scope(name).get<DomainService, S>(key ?: io.tagd.di.key())
}

/**
 * Data - Repositories Access
 */
inline fun <reified S : Repository> Module.repository(key: Key<S>? = null): S? {
    return Scope(name).get<Repository, S>(key ?: io.tagd.di.key())
}

/**
 * Data - Gateways Access
 */
inline fun <reified S : Gateway> Module.gateway(key: Key<S>? = null): S? {
    return Scope(name).get<Gateway, S>(key ?: io.tagd.di.key())
}

/**
 * Data - Daos Access
 */
inline fun <reified S : DataAccessObject> Module.dao(key: Key<S>? = null): S? {
    return Scope(name).get<DataAccessObject, S>(key ?: io.tagd.di.key())
}

/**
 * Data - Cache Access
 */
inline fun <reified S : Cache<*>> Module.cache(key: Key<S>? = null): S? {
    return Scope(name).get<Cache<*>, S>(key ?: io.tagd.di.key())
}

/**
 * Vertical - CrossCutting Access
 */
inline fun <reified S : CrossCutting> Module.crosscutting(key: Key<S>? = null): S? {
    return Scope(name).get<CrossCutting, S>(key ?: io.tagd.di.key())
}

/**
 * Vertical - Reference Access
 */
inline fun <T, reified S : ReferenceHolder<T>> Module.reference(key: Key<S>? = null): T? {
    return (Scope(name).get<ReferenceHolder<T>, S>(key ?: io.tagd.di.key()))?.value
}