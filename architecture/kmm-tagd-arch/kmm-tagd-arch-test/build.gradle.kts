plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":architecture:kmm-tagd-arch"))
            }
        }
    }
}

android {
    namespace = "io.tagd.arch.test"
}

pomBuilder {
    description.set("The technology agnostic architecture's definition tests mocks")
}