package io.tagd.langx.reflection

import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

actual typealias Type = java.lang.reflect.Type

actual inline fun <reified T: Any> nativeTypeOf(): Type {
    return object : TypeToken<T>() {}.type
}