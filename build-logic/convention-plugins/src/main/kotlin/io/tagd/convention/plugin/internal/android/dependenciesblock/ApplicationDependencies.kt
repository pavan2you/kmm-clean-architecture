package io.tagd.convention.plugin.internal.android.dependenciesblock

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidApplicationDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
//        add("coreLibraryDesugaring", libs.findLibrary("jdk8plus.libs.desugar").get())
//        add("implementation", libs.findLibrary("tagd.android").get())

        add("implementation", libs.findLibrary("android.material").get())
        add("implementation", libs.findLibrary("androidx.appcompat").get())
        add("implementation", libs.findLibrary("androidx.core.ktx").get())
    }
}