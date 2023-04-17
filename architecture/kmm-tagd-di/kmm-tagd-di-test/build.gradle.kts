plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":architecture:kmm-tagd-di"))
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