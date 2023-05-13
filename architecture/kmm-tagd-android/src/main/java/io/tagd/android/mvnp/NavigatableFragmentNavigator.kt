package io.tagd.android.mvnp

import android.app.Activity
import androidx.fragment.app.Fragment
import io.tagd.arch.present.mvnp.Navigatable
import io.tagd.arch.present.mvnp.NavigateToBuilder
import io.tagd.arch.present.mvnp.Navigator
import io.tagd.langx.Intent
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

open class NavigatableFragmentNavigator<N : NavigatableFragment<*, *, *>>(navigatable: N) :
    Navigator<N> {

    private var weakNavigatable: WeakReference<N>? = WeakReference(navigatable)

    override val navigatable: N?
        get() = weakNavigatable?.get()

    override fun navigateTo(intent: Intent) {
        ((navigatable as? Fragment))?.startActivity(intent)
    }

    override fun navigateTo(pathWithArgs: String, builder: NavigateToBuilder?) {
        throw UnsupportedOperationException("todo")
    }

    override fun navigateTo(kclass: KClass<out Navigatable>, builder: NavigateToBuilder?) {
        assert(kclass.java.isAssignableFrom(Activity::class.java))

        val fragment = navigatable as? Fragment
        fragment?.apply {
            val intent = android.content.Intent(activity, kclass.java)
            builder?.appendTo(intent)
            startActivity(intent)
        }
    }

    override fun navigateImplicitlyTo(action: String, builder: NavigateToBuilder?) {
        val fragment = navigatable as? Fragment
        fragment?.apply {
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
        weakNavigatable?.clear()
        weakNavigatable = null
    }
}