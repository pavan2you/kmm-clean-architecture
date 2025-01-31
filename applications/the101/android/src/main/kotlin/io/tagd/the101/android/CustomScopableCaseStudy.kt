package io.tagd.the101.android

import io.tagd.arch.app.LoadingStateHandler
import io.tagd.arch.scopable.AbstractWithinScopableInitializer
import io.tagd.arch.scopable.AbstractWithinScopableInjector
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.WithinScopableInitializer
import io.tagd.core.Dependencies
import io.tagd.di.Scope
import io.tagd.di.layer
import io.tagd.langx.Callback

/**
 * The custom scopable
 */
class SampleProduct(
    override val outerScope: Scope,
    override val thisScope: Scope,
    override val name: String,
) : Scopable {

    var withinInjector: WithinSampleProductInjector? = null

    override fun release() {
        withinInjector?.release()
        withinInjector = null
    }
}

/**
 * The within custom scopable injector
 */
class WithinSampleProductInjector(within: SampleProduct) :
    AbstractWithinScopableInjector<SampleProduct>(within) {

    override fun load(initializers: ArrayList<WithinScopableInitializer<SampleProduct, *>>) {
        initializers.add(SampleModuleInitializer(within))
    }

    override fun <WITHIN : Scopable> canRegisterLoadingSteps(
        handler: LoadingStateHandler<WITHIN, *, *>
    ): Boolean {

        return false
    }
}

/**
 * The custom scopable initializer
 */
class SampleProductInitializer<S : Scopable>(within : S) :
    AbstractWithinScopableInitializer<S, SampleProduct>(within) {

    private var withinInjector: WithinSampleProductInjector? = null

    override fun initialize(callback: Callback<Unit>) {
        val product = new(newDependencies())
        with(outerScope) {
            layer<Scopable> {
                bind<SampleProduct>().toInstance(product)
            }
        }
        withinInjector = product.withinInjector
        withinInjector?.initialize(callback)
    }

    override fun new(dependencies: Dependencies): SampleProduct {
        val outerScope = dependencies.get<Scope>(ARG_OUTER_SCOPE)!!
        return SampleProduct(
            outerScope = outerScope,
            thisScope = outerScope.addSubScopeIfAbsent("sample-product"),
            name = "sample-product",
        ).also {
            it.withinInjector = WithinSampleProductInjector(it)
        }
    }

    override fun <WITHIN : Scopable> registerLoadingSteps(
        handler: LoadingStateHandler<WITHIN, *, *>,
        callback: Callback<Unit>
    ) {
        withinInjector?.registerLoadingSteps(handler, callback)
    }

    override fun inject(callback: Callback<Unit>) {
        withinInjector?.inject(callback)
    }

    override fun release() {
        withinInjector = null
        super.release()
    }
}
