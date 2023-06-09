package io.tagd.langx

import android.annotation.SuppressLint
import android.os.Build

actual typealias Locale = java.util.Locale

@SuppressLint("ObsoleteSdkInt")
actual fun Locale.asTwoLetterTag(): String {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Locale.getDefault().toLanguageTag()         // ---> en-US
        } else {
            val locale = Locale.getDefault().toString() // ---> en_US
            locale.replace("_", "-")   // ---> en-US
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "UNKNOWN"
    }
}