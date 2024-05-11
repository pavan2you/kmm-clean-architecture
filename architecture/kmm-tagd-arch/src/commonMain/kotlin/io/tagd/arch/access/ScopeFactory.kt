/*
 * Copyright (C) 2021 The TagD Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.tagd.arch.access

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
import io.tagd.arch.module.Module
import io.tagd.arch.present.service.PresentationService
import io.tagd.core.State
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.Scopable
import io.tagd.di.Scope
import io.tagd.di.create
import io.tagd.di.get
import io.tagd.di.key2
import io.tagd.di.layer

fun scope(name: String, parent: Scope? = null): Scope? {
    return if (name == Global.name) Global else (parent ?: Global).subScope(name)
}

/**
 * Scopable Access
 */
inline fun <reified S : Scopable> Scope.scopable(key: Key<S>? = null): S? {
    return this.get<Scopable, S>(key ?: io.tagd.di.key())
}

/**
 * Module Access
 */
inline fun <reified S : Module> Scope.module(key: Key<S>? = null): S? {
    return this.get<Module, S>(key ?: io.tagd.di.key())
}

/**
 * Library Access
 */
inline fun <reified S : Library> Scope.library(key: Key<S>? = null): S? {
    return this.get<Library, S>(key ?: io.tagd.di.key())
}

/**
 * Infra Access
 */
inline fun <reified S : InfraService> Scope.infraService(key: Key<S>? = null): S? {
    return this.get<InfraService, S>(key ?: io.tagd.di.key())
}

inline fun <reified S : InfraService> Scope.createInfra(
    key: Key<S>? = null,
    state: State? = null
): S {
    return this.create(key ?: io.tagd.di.key(), state)
}

/**
 * Presentation Access
 */
inline fun <reified S : PresentationService> Scope.presentationService(key: Key<S>? = null): S? {
    return this.get<PresentationService, S>(key ?: io.tagd.di.key())
}

/**
 * Domain - Usecases Access
 */
inline fun <reified S : Command<*, *>> Scope.usecase(key: Key<S>? = null): S? {
    return this.get<Command<*, *>, S>(key ?: io.tagd.di.key())
}

/**
 * Domain - Services Access
 */
inline fun <reified S : DomainService> Scope.domainService(key: Key<S>? = null): S? {
    return this.get<DomainService, S>(key ?: io.tagd.di.key())
}

/**
 * Data - Repositories Access
 */
inline fun <reified S : Repository> Scope.repository(key: Key<S>? = null): S? {
    return this.get<Repository, S>(key ?: io.tagd.di.key())
}

/**
 * Data - Gateways Access
 */
inline fun <reified S : Gateway> Scope.gateway(key: Key<S>? = null): S? {
    return this.get<Gateway, S>(key ?: io.tagd.di.key())
}

/**
 * Data - Daos Access
 */
inline fun <reified S : DataAccessObject> Scope.dao(key: Key<S>? = null): S? {
    return this.get<DataAccessObject, S>(key ?: io.tagd.di.key())
}

/**
 * Data - Cache Access
 */
inline fun <reified S : Cache<*>> Scope.cache(key: Key<S>? = null): S? {
    return this.get<Cache<*>, S>(key ?: io.tagd.di.key())
}

/**
 * Vertical - CrossCutting Access
 */
inline fun <reified S : CrossCutting> Scope.crosscutting(key: Key<S>? = null): S? {
    return this.get<CrossCutting, S>(key ?: io.tagd.di.key())
}

/**
 * Vertical - Reference Access
 */
inline fun <reified S : ReferenceHolder<*>> Scope.referenceHolder(key: Key<S>? = null): S? {
    return this.get<ReferenceHolder<*>, S>(key ?: io.tagd.di.key())
}

inline fun <T, reified S : ReferenceHolder<T>> Scope.reference(key: Key<S>? = null): T? {
    return referenceHolder(key)?.value
}

inline fun <reified T : Any> Scope.bindReference(reference: T) {
    layer<ReferenceHolder<*>> {
        bind(key2<ReferenceHolder<T>, T>()).toInstance(ReferenceHolder(reference))
    }
}