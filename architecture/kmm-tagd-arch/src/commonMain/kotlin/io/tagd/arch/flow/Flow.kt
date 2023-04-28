package io.tagd.arch.flow

import io.tagd.core.Service

interface Flow : Service {

    fun trigger()
}