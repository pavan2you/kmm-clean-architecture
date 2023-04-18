package io.tagd.langx

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
actual fun <T : Any> KClass<T>.forName(klass: String): KClass<T> {
    return Class.forName(klass).kotlin as KClass<T>
}