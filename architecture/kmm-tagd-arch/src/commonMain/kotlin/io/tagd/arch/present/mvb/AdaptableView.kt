package io.tagd.arch.present.mvb

interface AdaptableView : Attachable, Detachable, Recyclable {

    fun onCreate()

    fun onDestroy()
}