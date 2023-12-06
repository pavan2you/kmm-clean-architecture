package io.tagd.langx.reflection

import kotlin.reflect.KClass

actual interface Type

actual fun <T> nativeTypeOf(): Type {
    TODO("Not yet implemented")
}

actual fun <T : Any> KClass<T>.nativeType(): Type {
    TODO("Not yet implemented")
}