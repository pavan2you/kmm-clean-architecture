plugins {
    id("io.tagd.android.library")
}

android {
    namespace = "io.tagd.android.test"

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
    api(project(":architecture:kmm-tagd-android"))
}

pomBuilder {
    description.set("The technology agnostic architecture android implementation")
    siblings.set(listOf(
        "kmm-tagd-android",
        "kmm-tagd-core",
        "kmm-tagd-core-test",
        "kmm-tagd-arch",
        "kmm-tagd-arch-test",
        "kmm-tagd-di",
        "kmm-tagd-di-test",
        "kmm-langx",
        "kmm-langx-test",
        "lang-kotlinx",
        "lang-kotlinx-test",
        "lang-androidx",
        "lang-androidx-test",
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
    ))
}
