package io.tagd.langx

import android.annotation.SuppressLint
import android.os.Build

actual class Locale {

    private lateinit var delegate: java.util.Locale

    @SuppressLint("ObsoleteSdkInt")
    actual fun asTwoLetterTag(): String {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                java.util.Locale.getDefault().toLanguageTag()         // ---> en-US
            } else {
                val locale = java.util.Locale.getDefault().toString() // ---> en_US
                locale.replace("_", "-")   // ---> en-US
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "UNKNOWN"
        }
    }

    actual companion object {

        actual fun get(languageTag: String): Locale {
            return Locale().apply {
                delegate = java.util.Locale.forLanguageTag(languageTag)
            }
        }

        actual fun default(): Locale {
            return Locale().apply {
                delegate = java.util.Locale.getDefault()
            }
        }
    }
}