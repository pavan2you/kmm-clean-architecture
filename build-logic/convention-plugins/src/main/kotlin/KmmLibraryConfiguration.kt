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
        android {
            compilations.all {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
            publishLibraryVariants("release", "debug")
        }

        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach {
            it.binaries.framework {
                baseName = "${project.name}"
            }
        }

        sourceSets {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            val commonMain by getting
            val commonTest by getting {
                dependencies {
                    implementation(kotlin("test"))
                }
            }
            val androidMain by getting
            val androidUnitTest by getting {
                dependencies {
                    implementation(libs.findLibrary("junit4").get())
                    implementation(libs.findLibrary("mockito.core").get())
                    implementation(libs.findLibrary("mockito.inline").get())
                    implementation(libs.findLibrary("mockito.kotlin").get())
                }
            }
            val iosX64Main by getting
            val iosArm64Main by getting
            val iosSimulatorArm64Main by getting
            val iosMain by creating {
                dependsOn(commonMain)
                iosX64Main.dependsOn(this)
                iosArm64Main.dependsOn(this)
                iosSimulatorArm64Main.dependsOn(this)
            }
            val iosX64Test by getting
            val iosArm64Test by getting
            val iosSimulatorArm64Test by getting
            val iosTest by creating {
                dependsOn(commonTest)
                iosX64Test.dependsOn(this)
                iosArm64Test.dependsOn(this)
                iosSimulatorArm64Test.dependsOn(this)
            }
        }
    }
}

fun KotlinMultiplatformExtension.sourceSets(configure: Action<NamedDomainObjectContainer<KotlinSourceSet>>) {
    (this as ExtensionAware).extensions.configure("sourceSets", configure)
}
