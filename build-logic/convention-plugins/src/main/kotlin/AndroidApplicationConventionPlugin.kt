import com.android.build.api.dsl.ApplicationExtension
import io.tagd.convention.plugin.internal.android.dependenciesblock.configureAndroidApplicationDependencies
import io.tagd.convention.plugin.internal.android.androidblock.configureBuildTypesInAndroidApplicationBlock
import io.tagd.convention.plugin.internal.android.androidblock.configureKotlinInAndroidBlock
import io.tagd.convention.plugin.internal.android.androidblock.configurePackageOptionsInAndroidBlock
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
//                apply("convention.secrets")
            }

//            repositories {
//                google()
//                mavenCentral()
//                mavenLocal()
//                maven {
//                    url = uri("https://maven.pkg.github.com/pavan2you/kmm-clean-architecture")
//
//                    credentials {
//                        username = extra["githubUser"] as? String
//                        password = extra["githubToken"] as? String
//                    }
//                }
//            }

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = 33
                configureKotlinInAndroidBlock(this)
                configurePackageOptionsInAndroidBlock(this)
                configureBuildTypesInAndroidApplicationBlock(this)
            }
            configureAndroidApplicationDependencies()
        }
    }
}