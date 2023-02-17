plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false

    id("convention.secrets")
}

repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://maven.pkg.github.com/pavan2you/kmm-clean-architecture")

        credentials {
            username = extra["githubUser"] as? String
            password = extra["githubToken"] as? String
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
