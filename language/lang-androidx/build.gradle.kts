plugins {
    id("io.tagd.android.library")
}

android {
    namespace = "io.tagd.androidx"

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
    implementation(project(":language:kmm-langx"))
    testImplementation(project(":language:lang-androidx:lang-androidx-test"))
}

pomBuilder {
    description.set("The technology agnostic android's extensions")
}