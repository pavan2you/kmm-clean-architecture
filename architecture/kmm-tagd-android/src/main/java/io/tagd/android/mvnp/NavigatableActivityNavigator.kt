package io.tagd.android.mvnp

import android.app.Activity
import io.tagd.arch.present.mvnp.Navigatable
import io.tagd.arch.present.mvnp.NavigateToBuilder
import io.tagd.arch.present.mvnp.Navigator
import io.tagd.langx.Intent
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

open class NavigatableActivityNavigator<N : NavigatableActivity<*, *, *>>(navigatable: N) :
    Navigator<N> {

    private var navigatableReference: WeakReference<N>? = WeakReference(navigatable)

    override val navigatable: N?
        get() = navigatableReference?.get()

    override fun navigateTo(intent: Intent) {
        (navigatable as? Activity)?.startActivity(intent)
    }

    override fun navigateTo(pathWithArgs: String, builder: NavigateToBuilder?) {
        throw UnsupportedOperationException("todo")
    }

    override fun navigateTo(kclass: KClass<out Navigatable>, builder: NavigateToBuilder?) {
        assert(kclass.java.isAssignableFrom(Activity::class.java))

        (navigatable as? Activity)?.apply {
            val intent = android.content.Intent(this, kclass.java)
            builder?.appendTo(intent)
            startActivity(intent)
        }
    }

    override fun navigateImplicitlyTo(action: String, builder: NavigateToBuilder?) {
        (navigatable as? Activity)?.apply {
            try {
                val intent = android.content.Intent(action)
                builder?.appendTo(intent)
                startActivity(intent)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun release() {
        navigatableReference?.clear()
        navigatableReference = null
    }
}