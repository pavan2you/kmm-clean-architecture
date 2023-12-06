package io.tagd.langx.reflection

import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

actual typealias Type = java.lang.reflect.Type

actual fun <T> nativeTypeOf(): Type {
    return object : TypeToken<T>() {}.type
}

actual fun <T : Any> KClass<T>.nativeType(): Type {
    return object : TypeToken<T>() {}.type
}