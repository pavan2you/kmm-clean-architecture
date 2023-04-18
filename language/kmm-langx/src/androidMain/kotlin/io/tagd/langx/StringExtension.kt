package io.tagd.langx

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
actual fun <T : Any> String.asKClass(): KClass<T> {
    return Class.forName(this).kotlin as KClass<T>
}