plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":architecture:kmm-tagd-core"))
            }
        }
    }
}

android {
    namespace = "io.tagd.core"
}

pomBuilder {
    description.set("The technology agnostic architecture's definition")
}