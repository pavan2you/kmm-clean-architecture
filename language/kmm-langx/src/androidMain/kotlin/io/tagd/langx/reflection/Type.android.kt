@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.reflection

import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

actual typealias Type = java.lang.reflect.Type

actual inline fun <reified T: Any> nativeTypeOf(): Type {
    return object : TypeToken<T>() {}.type
}