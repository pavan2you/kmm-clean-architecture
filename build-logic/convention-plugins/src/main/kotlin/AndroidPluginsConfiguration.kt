import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinInAndroidBlock(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 33

        defaultConfig {
            minSdk = 21
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
            //isCoreLibraryDesugaringEnabled = true
        }

        kotlinOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()

            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.RequiresOptIn",
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-opt-in=kotlinx.coroutines.FlowPreview",
                "-opt-in=kotlin.Experimental",
            )

            // Set JVM target to 8
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

private fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}

internal fun Project.configurePackageOptionsInAndroidBlock(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        packagingOptions {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }
}

internal fun Project.configureBuildTypesInAndroidBlock(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
            }
        }
    }
}

internal fun Project.configureAndroidApplicationDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
//        add("coreLibraryDesugaring", libs.findLibrary("jdk8plus.libs.desugar").get())
//        add("implementation", libs.findLibrary("tagd.android").get())

        add("implementation", libs.findLibrary("android.material").get())
        add("implementation", libs.findLibrary("androidx.appcompat").get())
        add("implementation", libs.findLibrary("androidx.core.ktx").get())
    }
}

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
        add("androidTestImplementation", libs.findLibrary("androidx.test.ext").get())
        add("androidTestImplementation", libs.findLibrary("androidx.test.espresso.core").get())

        add("testImplementation", kotlin("test"))
        add("testImplementation", libs.findLibrary("mockito.core").get())
        add("testImplementation", libs.findLibrary("mockito.inline").get())
        add("testImplementation", libs.findLibrary("mockito.kotlin").get())
    }
}

