plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("convention.publish.aar")
}

android {
    namespace = "io.tagd.android"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(":architecture:kmm-tagd-arch"))
    api(project(":language:lang-kotlinx"))
    api(project(":language:lang-androidx"))

    api("androidx.core:core-ktx:1.9.0")
    api("androidx.appcompat:appcompat:1.6.1")
    api("com.google.android.material:material:1.8.0")

    api("com.google.guava:guava:31.0.1-android")

    testApi("junit:junit:4.13.2")
    androidTestApi("androidx.test.ext:junit:1.1.5")
    androidTestApi("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {
    publications.withType<MavenPublication> {
        pom {
            description.set("The technology agnostic architecture android implementation")
            withXml {
                val dependencies = asNode().appendNode("dependencies")

                val siblingDependencies = arrayListOf(
                    "kmm-tagd-core",
                    "kmm-tagd-arch",
                    "kmm-tagd-di",
                    "kmm-lang-langx",
                    "lang-kotlinx",
                    "lang-androidx"
                )
                siblingDependencies.forEach {
                    val dependency = dependencies.appendNode("dependency")
                    dependency.appendNode("groupId", extra["artifactGroupId"])
                    dependency.appendNode("artifactId", it)
                    dependency.appendNode("version", extra["artifactVersionName"])
                }

                // include any transitive dependencies
                configurations.implementation.get().allDependencies.forEach {
                    handleDependencies(it, dependencies)
                }
                configurations.api.get().allDependencies.forEach {
                    handleDependencies(it, dependencies)
                }
            }
        }
    }
}

fun handleDependencies(dep: Dependency, dependencies: groovy.util.Node) {

    if (dep.group == null
        || dep.group == "com.android.databinding"
        || !dep.name.startsWith("kmm-")
        || !dep.name.startsWith("lang-")
        || dep.version == "unspecified") {
        println("ignoring dependency ${dep.group}:${dep.name}:${dep.version}")
        return
    }

    val dependency = dependencies.appendNode("dependency")
    dependency.appendNode("groupId", dep.group)
    println(dep.group)
    println(dep.version)
    println(dep.name)
    dependency.appendNode(extra["artifactId"], dep.name)
    dependency.appendNode(version, dep.version)
}
