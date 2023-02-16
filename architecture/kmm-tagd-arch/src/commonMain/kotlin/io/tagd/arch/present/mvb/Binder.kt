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

package io.tagd.arch.present.mvb

import io.tagd.arch.data.DataObject
import io.tagd.core.Releasable

/**
 * Represents Binder in Model-View-Binder(ViewModel).
 *
 * The primary responsibility is to glue model to the view. Optionally / in complete sense, it will
 * listen to UI changes and emits / delegates to model emitters to produce a new model.
 *
 * To handle ui changes, the recommendation is to implement onChangeXXX(xxxValue) { ... }
 * Or having a template method onChange(elementId, elementValue) { ... }
 */
interface Binder<T : DataObject, V : BindableView<T>> : Releasable {

    val view: V?

    fun onCreate()

    fun onBind(model: T, vararg optionals: Any?)

    fun onUnbind()

    fun onDestroy()
}