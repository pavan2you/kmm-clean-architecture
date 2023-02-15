plugins {
    id("com.android.application").version("8.1.0-alpha04").apply(false)
    id("com.android.library").version("8.1.0-alpha04").apply(false)
    kotlin("android").version("1.8.0").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
    id("org.jetbrains.kotlin.jvm").version("1.8.0").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
