@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx

expect class Locale {

    fun asTwoLetterTag(): String

    companion object {

        fun get(languageTag: String): Locale

        fun default(): Locale
    }
}