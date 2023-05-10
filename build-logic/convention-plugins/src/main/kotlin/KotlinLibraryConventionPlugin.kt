import io.tagd.convention.plugin.internal.kotlin.configureKotlinLibraryDependencies
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

class KotlinLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
                apply("convention.publish.jar")
            }

            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }

            tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_1_8.toString()
                }
            }

            configureKotlinLibraryDependencies()

            val pomBuilderExtension: MavenPomBuilderExtension = project.extensions.create(
                "pomBuilder",
                MavenPomBuilderExtension::class.java
            )
            extensions.configure<PublishingExtension> {
                publications.withType(MavenPublication::class.java) {
                    this as MavenPublication
                    pom {
                        val setDescription = pomBuilderExtension.description.orNull
                            ?: "The technology agnostic's kotlin lib"
                        description.set(setDescription)
                    }
                }
            }
        }
    }
}