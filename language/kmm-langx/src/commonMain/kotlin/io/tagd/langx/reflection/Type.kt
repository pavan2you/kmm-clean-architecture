package io.tagd.langx.reflection

import kotlin.reflect.KClass

expect interface Type

expect fun <T> nativeTypeOf(): Type

expect fun <T : Any> KClass<T>.nativeType(): Type