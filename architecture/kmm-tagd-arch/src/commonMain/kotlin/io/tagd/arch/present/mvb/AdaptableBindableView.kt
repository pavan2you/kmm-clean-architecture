package io.tagd.arch.present.mvb

import io.tagd.arch.datatype.DataObjectable

interface AdaptableBindableView<T : DataObjectable> : Adaptable, BindableView<T>