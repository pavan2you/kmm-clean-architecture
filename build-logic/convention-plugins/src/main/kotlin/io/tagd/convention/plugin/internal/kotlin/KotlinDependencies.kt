package io.tagd.convention.plugin.internal.kotlin

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

internal fun Project.configureKotlinLibraryDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    configurations.configureEach {
        resolutionStrategy {
            force(libs.findLibrary("junit4").get())
            // Temporary workaround for https://issuetracker.google.com/174733673
            force("org.objenesis:objenesis:2.6")
        }
    }

    dependencies {
        add("api", libs.findLibrary("kotlinx.coroutines.core").get())
        add("api", libs.findLibrary("kotlinx.coroutines.android").get())

        add("testImplementation", kotlin("test"))
        add("testImplementation", libs.findLibrary("mockito.core").get())
        add("testImplementation", libs.findLibrary("mockito.inline").get())
        add("testImplementation", libs.findLibrary("mockito.kotlin").get())
    }
}