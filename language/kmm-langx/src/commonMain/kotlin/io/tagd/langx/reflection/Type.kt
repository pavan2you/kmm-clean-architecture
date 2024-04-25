@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.reflection

expect interface Type

expect inline fun <reified T: Any> nativeTypeOf(): Type