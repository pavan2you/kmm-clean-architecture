package io.tagd.arch.present.mvnp

import io.tagd.core.Service

interface Navigatable : Service {

    fun navigator(): Navigator<Navigatable>?
}