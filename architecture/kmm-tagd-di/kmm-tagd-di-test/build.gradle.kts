plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":architecture:kmm-tagd-di"))
                api(project(":architecture:kmm-tagd-core:kmm-tagd-core-test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.mockito.kotlin)
            }
        }
    }
}

android {
    namespace = "io.tagd.di.test"
}

pomBuilder {
    description.set("The technology agnostic architecture's di's test definition")
}