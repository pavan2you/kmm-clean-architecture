
import com.android.build.api.dsl.LibraryExtension
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
                compileSdk = 33
                defaultConfig {
                    minSdk = 21
                    targetSdk = 33
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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