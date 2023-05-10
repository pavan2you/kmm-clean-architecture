package io.tagd.convention.plugin.internal.android.dependenciesblock

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

internal fun Project.configureAndroidLibraryDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    configurations.configureEach {
        resolutionStrategy {
            force(libs.findLibrary("junit4").get())
            // Temporary workaround for https://issuetracker.google.com/174733673
            force("org.objenesis:objenesis:2.6")
        }
    }

    dependencies {
//        add("coreLibraryDesugaring", libs.findLibrary("jdk8plus.libs.desugar").get())
        add("api", libs.findLibrary("android.material").get())
        add("api", libs.findLibrary("androidx.appcompat").get())
        add("api", libs.findLibrary("androidx.core.ktx").get())

        add("androidTestImplementation", kotlin("test"))
        add(
            "androidTestImplementation",
            libs.findLibrary("androidx.test.junit.ext").get()
        )
        add(
            "androidTestImplementation",
            libs.findLibrary("androidx.test.espresso.core").get()
        )

        add("testImplementation", kotlin("test"))
        add("testImplementation", libs.findLibrary("mockito.core").get())
        add("testImplementation", libs.findLibrary("mockito.inline").get())
        add("testImplementation", libs.findLibrary("mockito.kotlin").get())
    }
}