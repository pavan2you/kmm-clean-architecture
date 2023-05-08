package io.tagd.arch.present.mvnp

import io.tagd.core.Service

interface Navigatable : Service {

    fun <N : Navigatable> navigator(): Navigator<N>?
}