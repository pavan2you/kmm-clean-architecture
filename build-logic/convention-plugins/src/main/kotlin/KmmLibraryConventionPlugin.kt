
import com.android.build.api.dsl.LibraryExtension
import io.tagd.convention.plugin.internal.kmm.configureKmmKotlinBlock
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.multiplatform")
                apply("convention.publish.kmm")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                configureKmmKotlinBlock(this)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 34
                defaultConfig {
                    minSdk = 21
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            }

            val pomBuilderExtension: MavenPomBuilderExtension = project.extensions.create(
                "pomBuilder",
                MavenPomBuilderExtension::class.java
            )
            extensions.configure<PublishingExtension> {
                publications.withType(MavenPublication::class.java) {
                    this as MavenPublication
                    pom {
                        val setDescription = pomBuilderExtension.description.orNull
                            ?: "The technology agnostic's kmm lib"
                        description.set(setDescription)
                    }
                }
            }
        }
    }
}