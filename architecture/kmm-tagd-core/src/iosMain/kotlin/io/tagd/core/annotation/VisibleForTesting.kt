@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.core.annotation

@Retention(AnnotationRetention.BINARY)
actual annotation class VisibleForTesting actual constructor(actual val otherwise: Visibility)