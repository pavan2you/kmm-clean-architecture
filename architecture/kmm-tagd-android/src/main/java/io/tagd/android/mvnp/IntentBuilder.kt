package io.tagd.android.mvnp

import android.os.Bundle
import io.tagd.arch.present.mvnp.NavigateToBuilder
import io.tagd.langx.Intent

class IntentBuilder() : NavigateToBuilder {

    var flags: Int = 0
    val extras: Bundle = Bundle()

    override fun appendTo(intent: Intent) {
        intent.addFlags(flags)
        intent.putExtras(extras)
    }
}