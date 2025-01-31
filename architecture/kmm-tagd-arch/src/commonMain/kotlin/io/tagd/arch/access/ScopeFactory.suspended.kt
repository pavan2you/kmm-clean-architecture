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
import io.tagd.core.CrossCutting
import io.tagd.arch.domain.service.DomainService
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.infra.InfraService
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.arch.present.service.PresentationService
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.library.Library
import io.tagd.arch.scopable.module.Module
import io.tagd.core.State
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.Scope
import io.tagd.di.create
import io.tagd.di.get
import io.tagd.di.getAsync
import io.tagd.di.getLazy
import io.tagd.di.getLazyAsync
import io.tagd.di.key2
import io.tagd.di.layer

/**
 * Scopable Access
 */
suspend inline fun <reified S : Scopable> Scope.scopableAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<Scopable, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<Scopable, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Module Access
 */
suspend inline fun <reified S : Module> Scope.moduleAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<Module, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<Module, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Library Access
 */
suspend inline fun <reified S : Library> Scope.libraryAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<Library, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<Library, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Infra Access
 */
suspend inline fun <reified S : InfraService> Scope.infraServiceAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<InfraService, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<InfraService, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Presentation Access
 */
suspend inline fun <reified S : PresentationService> Scope.presentationServiceAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<PresentationService, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<PresentationService, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Domain - Usecases Access
 */
suspend inline fun <reified S : Command<*, *>> Scope.usecaseAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<Command<*, *>, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<Command<*, *>, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Domain - Services Access
 */
suspend inline fun <reified S : DomainService> Scope.domainServiceAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<DomainService, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<DomainService, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Data - Repositories Access
 */
suspend inline fun <reified S : Repository> Scope.repositoryAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<Repository, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<Repository, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Data - Gateways Access
 */
suspend inline fun <reified S : Gateway> Scope.gatewayAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<Gateway, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<Gateway, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Data - Daos Access
 */
suspend inline fun <reified S : DataAccessObject> Scope.daoAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<DataAccessObject, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<DataAccessObject, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Data - Cache Access
 */
suspend inline fun <reified S : Cache<*>> Scope.cacheAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<Cache<*>, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<Cache<*>, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Vertical - CrossCutting Access
 */
suspend inline fun <reified S : CrossCutting> Scope.crosscuttingAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<CrossCutting, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<CrossCutting, S>(key ?: io.tagd.di.key())
    }
}

/**
 * Vertical - Reference Access
 */
suspend inline fun <reified S : ReferenceHolder<*>> Scope.referenceHolderAsync(
    key: Key<S>? = null,
    args: State? = null
): S? {

    return if (args != null) {
        this.getLazyAsync<ReferenceHolder<*>, S>(key ?: io.tagd.di.key(), args)
    } else {
        this.getAsync<ReferenceHolder<*>, S>(key ?: io.tagd.di.key())
    }
}

suspend inline fun <T, reified S : ReferenceHolder<T>> Scope.referenceAsync(
    key: Key<S>? = null,
    args: State? = null
): T? {

    return referenceHolderAsync(key, args)?.value
}