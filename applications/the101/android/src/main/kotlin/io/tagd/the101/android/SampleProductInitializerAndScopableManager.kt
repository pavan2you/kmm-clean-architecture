package io.tagd.the101.android

import io.tagd.arch.control.LoadingStateHandler
import io.tagd.langx.Callback
import io.tagd.arch.scopable.Scopable
import io.tagd.arch.scopable.ScopableInitializer
import io.tagd.arch.scopable.ScopableManager
import io.tagd.core.Dependencies
import io.tagd.core.dependencies
import io.tagd.di.Scope
import io.tagd.di.layer

class Product(
    override val outerScope: Scope,
    override val thisScope: Scope,
    override val name: String
) : Scopable {

    override fun release() {
        //no-op
    }

}

class SampleProductInitializerAndScopableManager : ScopableManager(), ScopableInitializer<Product> {

    override fun initialize(outer: Scopable, callback: Callback<Unit>) {
        val product = new(dependencies("OUTER_SCOPE" to outer.thisScope))
        with(outer.thisScope) {
            layer<Scopable> {
                bind<Product>().toInstance(product)
            }
        }
        super.initialize(product, callback)
    }

    override fun loadScopableInitializers(initializers: ArrayList<ScopableInitializer<*>>) {
        initializers.add(SampleModuleInitializer())
    }

    override fun <Outer : Scopable> canRegisterLoadingSteps(
        handler: LoadingStateHandler<Outer, *, *>
    ): Boolean {

        return false
    }

    override fun new(dependencies: Dependencies): Product {
        return Product(
            outerScope = dependencies.get<Scope>("OUTER_SCOPE")!!,
            thisScope = Scope("sample-product"),
            name = "sample-product"
        )
    }

    override fun release() {
    }
}