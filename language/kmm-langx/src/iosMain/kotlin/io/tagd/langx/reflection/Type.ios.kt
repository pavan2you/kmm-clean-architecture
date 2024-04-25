@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.reflection

actual interface Type

actual inline fun <reified T: Any> nativeTypeOf(): Type {
    TODO("Not yet implemented")
}