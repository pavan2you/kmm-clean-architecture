package io.tagd.arch.present.mvb

interface Windowable : Attachable, Detachable {

    fun onCreate()

    fun onDestroy()
}