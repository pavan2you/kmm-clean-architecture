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
import io.tagd.arch.domain.service.DomainService
import io.tagd.arch.domain.usecase.Command
import io.tagd.arch.infra.InfraService
import io.tagd.arch.infra.ReferenceHolder
import io.tagd.arch.present.service.PresentationService
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.library.Library
import io.tagd.arch.scopable.module.Module
import io.tagd.core.CrossCutting
import io.tagd.core.State
import io.tagd.di.Key
import io.tagd.di.Scope
import io.tagd.di.get
import io.tagd.di.getLazy
import io.tagd.langx.Callback

/**
 * Scopable Access
 */
inline fun <reified S : Scopable> Scope.scopable(key: Key<S>? = null, args: State? = null,
        noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<Scopable, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<Scopable, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Module Access
 */
inline fun <reified S : Module> Scope.module(key: Key<S>? = null, args: State? = null,
        noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<Module, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<Module, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Library Access
 */
inline fun <reified S : Library> Scope.library(key: Key<S>? = null, args: State? = null,
        noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<Library, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<Library, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Infra Access
 */
inline fun <reified S : InfraService> Scope.infraService(
    key: Key<S>? = null,
    args: State? = null,
    noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<InfraService, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<InfraService, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Presentation Access
 */
inline fun <reified S : PresentationService> Scope.presentationService(
    key: Key<S>? = null,
    args: State? = null,
    noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<PresentationService, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<PresentationService, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Domain - Usecases Access
 */
inline fun <reified S : Command<*, *>> Scope.usecase(key: Key<S>? = null, args: State? = null,
        noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<Command<*, *>, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<Command<*, *>, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Domain - Services Access
 */
inline fun <reified S : DomainService> Scope.domainService(
    key: Key<S>? = null,
    args: State? = null,
    noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<DomainService, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<DomainService, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Data - Repositories Access
 */
inline fun <reified S : Repository> Scope.repository(key: Key<S>? = null, args: State? = null,
        noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<Repository, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<Repository, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Data - Gateways Access
 */
inline fun <reified S : Gateway> Scope.gateway(key: Key<S>? = null, args: State? = null,
        noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<Gateway, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<Gateway, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Data - Daos Access
 */
inline fun <reified S : DataAccessObject> Scope.dao(key: Key<S>? = null, args: State? = null,
        noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<DataAccessObject, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<DataAccessObject, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Data - Cache Access
 */
inline fun <reified S : Cache<*>> Scope.cache(key: Key<S>? = null, args: State? = null,
        noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<Cache<*>, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<Cache<*>, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Vertical - CrossCutting Access
 */
inline fun <reified S : CrossCutting> Scope.crosscutting(
    key: Key<S>? = null,
    args: State? = null,
    noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<CrossCutting, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<CrossCutting, S>(key ?: io.tagd.di.key(), callback)
    }
}

/**
 * Vertical - Reference Access
 */
inline fun <reified S : ReferenceHolder<*>> Scope.referenceHolder(
    key: Key<S>? = null,
    args: State? = null,
    noinline callback: Callback<S?>) {

    if (args != null) {
        this.getLazy<ReferenceHolder<*>, S>(key ?: io.tagd.di.key(), args, callback)
    } else {
        this.get<ReferenceHolder<*>, S>(key ?: io.tagd.di.key(), callback)
    }
}

inline fun <T, reified S : ReferenceHolder<T>> Scope.reference(
    key: Key<S>? = null,
    args: State? = null,
    noinline callback: Callback<T?>
) {

    referenceHolder(key, args) {
        callback.invoke(it?.value)
    }
}