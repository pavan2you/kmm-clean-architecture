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
import io.tagd.core.Service
import io.tagd.core.State
import io.tagd.di.Global
import io.tagd.di.Key
import io.tagd.di.bind
import io.tagd.di.key

inline fun <reified T : Service, reified S : T> bind(key: Key<S>? = null, instance: S) {
    Global.bind<T, S>(key, instance)
}

/**
 * Module Access
 */
inline fun <reified S : Module> module(key: Key<S>? = null): S? {
    return Global.module(key)
}

/**
 * Library Access
 */
inline fun <reified S : Library> library(key: Key<S>? = null): S? {
    return Global.library(key)
}

/**
 * Infra Access
 */
inline fun <reified S : InfraService> infraService(key: Key<S>? = null): S? {
    return Global.infraService(key)
}

inline fun <reified S : InfraService> createInfra(key: Key<S>? = null, state: State? = null): S {
    return Global.createInfra(key, state)
}

/**
 * Presentation Access
 */
inline fun <reified S : PresentationService> presentationService(key: Key<S>? = null): S? {
    return Global.presentationService(key)
}

/**
 * Domain - Usecases Access
 */
inline fun <reified S : Command<*, *>> usecase(key: Key<S>? = null): S? {
    return Global.usecase(key)
}

/**
 * Domain - Services Access
 */
inline fun <reified S : DomainService> domainService(key: Key<S>? = null): S? {
    return Global.domainService(key)
}

/**
 * Data - Repositories Access
 */
inline fun <reified S : Repository> repository(key: Key<S>? = null): S? {
    return Global.repository(key)
}

/**
 * Data - Gateways Access
 */
inline fun <reified S : Gateway> gateway(key: Key<S>? = null): S? {
    return Global.gateway(key)
}

/**
 * Data - Daos Access
 */
inline fun <reified S : DataAccessObject> dao(key: Key<S>? = null): S? {
    return Global.dao(key)
}

/**
 * Data - Cache Access
 */
inline fun <reified S : Cache<*>> cache(key: Key<S>? = null): S? {
    return Global.cache(key)
}

/**
 * Vertical - CrossCutting Access
 */
inline fun <reified S : CrossCutting> crosscutting(key: Key<S>? = null): S? {
    return Global.crosscutting(key)
}

/**
 * Vertical - Reference Access
 */
inline fun <reified S : ReferenceHolder<*>> referenceHolder(key: Key<S>? = null): S? {
    return Global.referenceHolder(key)
}

inline fun <T, reified S : ReferenceHolder<T>> reference(key: Key<S>? = null): T? {
    return referenceHolder(key)?.value
}