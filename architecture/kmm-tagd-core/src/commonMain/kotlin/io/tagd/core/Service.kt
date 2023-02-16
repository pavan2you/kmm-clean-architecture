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

package io.tagd.core

/**
 * Any object which offers some behaviour, which can be consumed as part of business work flows
 * is a [Service].
 *
 * Services are
 *      - √ State less
 *      - √ Just supports behaviour / methods, but no state (or)
 *      - √ Just supports state, but not behaviour ==>
 *          In a rare phenomenon, service can be a state container, in this scenario it won't offer
 *          any behaviour, it just behaves as a state Lookup. However this is a discouraged or
 *          recommended to limit such practices.
 */
interface Service : Releasable