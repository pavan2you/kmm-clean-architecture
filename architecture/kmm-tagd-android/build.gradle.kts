plugins {
    id("io.tagd.android.library")
}

android {
    namespace = "io.tagd.android"

    defaultConfig {
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
}

dependencies {
    api(project(":architecture:kmm-tagd-arch"))
    api(project(":language:lang-kotlinx"))
    api(project(":language:lang-androidx"))

    api(libs.guava)
    api(libs.gson)
}

pomBuilder {
    description.set("The technology agnostic architecture android implementation")
    siblings.set(listOf(
        "kmm-tagd-core",
        "kmm-tagd-arch",
        "kmm-tagd-di",
        "kmm-langx",
        "lang-kotlinx",
        "lang-androidx"
    ))
    excludeGroups.set(listOf(
        "com.android.databinding"
    ))
    excludeVersions.set(listOf(
        "unspecified"
    ))
    includeStartsWith.set(listOf(
        "kmm-",
        "lang-",
        "guava",
        "gson"
    ))
}
