plugins {
    id("io.tagd.android.library")
}

android {
    namespace = "io.tagd.androidx.test"

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
    testImplementation(project(":language:lang-androidx"))
}

pomBuilder {
    description.set("The technology agnostic android's extensions test definition")
}