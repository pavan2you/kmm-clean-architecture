package io.tagd.arch.scopable.module

import io.tagd.arch.present.mvnp.Navigator

interface ModuleNavigator<out N : NavigatableModule> : Navigator<N>