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
    namespace = "io.tagd.core.test"
}

pomBuilder {
    description.set("The technology agnostic architecture's core traits tests definition")
}