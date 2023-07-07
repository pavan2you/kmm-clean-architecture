package io.tagd.langx

expect class Locale {

    fun asTwoLetterTag(): String

    companion object {

        fun get(languageTag: String): Locale

        fun default(): Locale
    }
}