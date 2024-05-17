package io.tagd.arch.scopable

import io.tagd.core.Nameable
import io.tagd.core.Service
import io.tagd.di.Scope

interface Scopable : Service, Nameable {

    val outerScope: Scope

    val thisScope: Scope
}