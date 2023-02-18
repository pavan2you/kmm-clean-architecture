import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.repositories

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
                configureBuildTypesInAndroidBlock(this)
            }
            configureAndroidApplicationDependencies()
        }
    }
}