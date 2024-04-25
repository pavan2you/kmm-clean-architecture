package io.tagd.langx.datatype

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
@Throws(Throwable::class)
actual fun <T : Any> String.asKClass(): KClass<T> {
    return Class.forName(this).kotlin as KClass<T>
}