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

package io.tagd.di.test

import com.nhaarman.mockito_kotlin.mock
import io.tagd.core.Service
import io.tagd.di.InjectDelegateProvider
import io.tagd.di.Key
import io.tagd.di.NullableInjectDelegateProvider
import io.tagd.di.Scope
import io.tagd.di.inject
import io.tagd.di.injectX
import io.tagd.di.typeOf
import kotlin.reflect.KProperty

inline fun <reified T : Service> Any.getInject(
    scope: String = Scope.GLOBAL_SCOPE,
    key: Key<T> = Key(typeOf<T>())
): T {

    val delegateProvider: InjectDelegateProvider<T> = inject(scope, key)
    val kProperty: KProperty<*> = mock()
    val delegate = delegateProvider.provideDelegate(this, kProperty)
    return delegate.getValue(this, kProperty)
}

inline fun <reified T : Service> Any.getInjectX(
    scope: String = Scope.GLOBAL_SCOPE,
    key: Key<T> = Key(typeOf<T>())
): T? {

    val delegateProvider: NullableInjectDelegateProvider<T> = injectX(scope, key)
    val kProperty: KProperty<*> = mock()
    val delegate = delegateProvider.provideDelegate(this, kProperty)
    return delegate.getValue(this, kProperty)
}