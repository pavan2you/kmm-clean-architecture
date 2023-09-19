package io.tagd.arch.present.mvb

import io.tagd.arch.data.DataObject

interface AdaptableBindableView<T : DataObject> : Adaptable, BindableView<T>