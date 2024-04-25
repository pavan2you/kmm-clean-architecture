@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.core.annotation

enum class Visibility(val value: Int) {
    /**
     * The annotated element would have "private" visibility
     */
    PRIVATE(2),

    /**
     * The annotated element would have "package private" visibility
     */
    PACKAGE_PRIVATE(3),

    /**
     * The annotated element would have "protected" visibility
     */
    PROTECTED(4), // Happens to be the same as Modifier.PROTECTED

    /**
     * The annotated element should never be called from production code, only from tests.
     *
     *
     * This is equivalent to `@RestrictTo.Scope.TESTS`.
     */
    NONE(5);
}

@Retention(AnnotationRetention.BINARY)
expect annotation class VisibleForTesting(val otherwise: Visibility = Visibility.PRIVATE)