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
    api(project(":language:lang-androidx"))
    api(project(":language:kmm-langx:kmm-langx-test"))
}

pomBuilder {
    description.set("The technology agnostic android's extensions test definition")
}