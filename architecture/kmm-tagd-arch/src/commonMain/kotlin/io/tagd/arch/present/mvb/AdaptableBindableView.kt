package io.tagd.arch.present.mvb

import io.tagd.arch.datatype.bind.BindableSubjectable

interface AdaptableBindableView<T : BindableSubjectable> : Adaptable, BindableView<T>