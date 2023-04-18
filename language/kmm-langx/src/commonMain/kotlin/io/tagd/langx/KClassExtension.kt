package io.tagd.langx

import kotlin.reflect.KClass


@Throws(Throwable::class)
expect fun <T : Any> KClass<T>.forName(klass: String): KClass<T>