package io.tagd.arch.control

import io.tagd.arch.scopable.Scopable

interface LoadingStateHandler<
    S : Scopable,
    STEPS : StepLooperSpec.Steps,
    D : StepLooperSpec.Dispatcher
> : StepLooperSpec<STEPS, D> {

    val scopable: S?
}

interface AppLoadingStateHandlerSpec<S : StepLooperSpec.Steps, D : StepLooperSpec.Dispatcher> :
    LoadingStateHandler<IApplication, S, D>