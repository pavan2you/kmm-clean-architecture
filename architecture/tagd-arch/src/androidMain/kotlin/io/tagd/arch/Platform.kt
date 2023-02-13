package io.tagd.arch

import java.util.concurrent.atomic.AtomicBoolean

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

val x = AtomicBoolean()