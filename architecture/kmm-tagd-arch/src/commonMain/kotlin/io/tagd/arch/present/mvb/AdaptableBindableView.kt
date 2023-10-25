package io.tagd.arch.present.mvb

import io.tagd.arch.data.DataObjectable

interface AdaptableBindableView<T : DataObjectable> : Adaptable, BindableView<T>