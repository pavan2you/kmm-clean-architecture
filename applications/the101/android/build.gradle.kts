plugins {
    id("io.tagd.android.application")
}

/*apply(from = "$rootDir/github_package_access.gradle")

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
}*/

android {
    namespace = "io.tagd.the101.android"

    defaultConfig {
        applicationId = "io.tagd.android"
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
}

dependencies {
    implementation(project(":architecture:kmm-tagd-android"))

    //implementation("io.github.pavan2you:kmm-tagd-android:0.0.0-alpha05-snapshot")
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)

    testImplementation(project(":architecture:kmm-tagd-android:kmm-tagd-android-test"))
}