package io.tagd.arch.scopable

import io.tagd.core.Initializer

interface ScopableInitializer<S : Scopable> : Initializer<S>, ScopableManagementSpec