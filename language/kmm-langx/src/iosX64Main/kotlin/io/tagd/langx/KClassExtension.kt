package io.tagd.langx

import kotlin.reflect.KClass

@Throws(Throwable::class)
actual fun <T : Any> KClass<T>.forName(klass: String): KClass<T> {
    TODO("Not yet implemented")
}