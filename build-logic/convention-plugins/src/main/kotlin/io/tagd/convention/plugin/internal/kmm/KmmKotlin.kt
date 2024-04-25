package io.tagd.convention.plugin.internal.kmm

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal fun Project.configureKmmKotlinBlock(
    kotlinExtension: KotlinMultiplatformExtension
) {

    kotlinExtension.apply {
        androidTarget {
            compilations.all {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
            publishLibraryVariants("release", "debug")
        }

        val enableIosTargets = extra["enableIosTargets"] as? Boolean ?: true

        if (enableIosTargets) {
            listOf(
                iosX64(),
                iosArm64(),
                iosSimulatorArm64()
            ).forEach {
                it.binaries.framework {
                    baseName = project.name
                    isStatic = true
                }
            }
        }

        sourceSets {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            commonTest.dependencies {
                implementation(kotlin("test"))
            }

            val androidUnitTest by getting
            androidUnitTest.dependencies {
                implementation(libs.findLibrary("junit4").get())
                implementation(libs.findLibrary("mockito.core").get())
                implementation(libs.findLibrary("mockito.inline").get())
                implementation(libs.findLibrary("mockito.kotlin").get())
            }

            val androidInstrumentedTest by getting
            androidInstrumentedTest.dependencies {
                implementation(libs.findLibrary("androidx.test.espresso.core").get())
                implementation(libs.findLibrary("androidx.test.junit.ext").get())
            }
        }

        task("testClasses")
    }
}

private fun KotlinMultiplatformExtension.sourceSets(
    configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>
) {
    (this as ExtensionAware).extensions.configure("sourceSets", configure)
}
