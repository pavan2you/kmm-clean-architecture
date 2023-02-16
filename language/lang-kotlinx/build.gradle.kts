plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("convention.publish.jar")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)

    testApi(libs.junit4)
    testApi(libs.mockito.core)
    testApi(libs.mockito.inline)
    testApi(libs.mockito.kotlin)
}

publishing {
    publications.withType<MavenPublication> {
        pom.description.set("The technology agnostic kotlin's extensions")
    }
}