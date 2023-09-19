package io.tagd.arch.present.mvb

interface AdaptableView<T : Any> : Adaptable {

    var model: T?

    fun show()

    fun hide()
}