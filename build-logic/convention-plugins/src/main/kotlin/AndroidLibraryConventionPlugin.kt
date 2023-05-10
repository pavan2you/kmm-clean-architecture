import com.android.build.api.dsl.LibraryExtension
import io.tagd.convention.plugin.internal.android.androidblock.configureBuildTypesInAndroidLibraryBlock
import io.tagd.convention.plugin.internal.android.androidblock.configureKotlinInAndroidBlock
import io.tagd.convention.plugin.internal.android.androidblock.configurePackageOptionsInAndroidBlock
import io.tagd.convention.plugin.internal.android.dependenciesblock.configureAndroidLibraryDependencies
import io.tagd.convention.plugin.internal.android.publishingblock.configurePomXmlWithDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("convention.publish.aar")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = 33
                configureKotlinInAndroidBlock(this)
                configurePackageOptionsInAndroidBlock(this)
                configureBuildTypesInAndroidLibraryBlock(this)
            }
            configureAndroidLibraryDependencies()

            val pomBuilderExtension: MavenPomBuilderExtension = project.extensions.create(
                "pomBuilder",
                MavenPomBuilderExtension::class.java
            )
            extensions.configure<PublishingExtension> {
                publications.withType(MavenPublication::class.java) {
                    pom {
                        val setDescription = pomBuilderExtension.description.orNull
                            ?: "The technology agnostic's kmm lib"
                        description.set(setDescription)

                        withXml {
                            configurePomXmlWithDependencies(this, pomBuilderExtension)
                        }
                    }
                }
            }
        }
    }
}