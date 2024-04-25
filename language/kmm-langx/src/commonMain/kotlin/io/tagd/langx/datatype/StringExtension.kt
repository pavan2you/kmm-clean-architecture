package io.tagd.langx.datatype

import kotlin.reflect.KClass

@Throws(Throwable::class)
expect fun <T : Any> String.asKClass(): KClass<T>