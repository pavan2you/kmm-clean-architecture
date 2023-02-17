plugins {
    id("groovy-gradle-plugin")
    `kotlin-dsl`
}

//java {
//    sourceCompatibility = JavaVersion.VERSION_11
//    targetCompatibility = JavaVersion.VERSION_11
//}

repositories {
    gradlePluginPortal()
    google()
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "io.tagd.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "io.tagd.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("kmmLibrary") {
            id = "io.tagd.kmm.library"
            implementationClass = "KmmLibraryConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "io.tagd.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
    }
}