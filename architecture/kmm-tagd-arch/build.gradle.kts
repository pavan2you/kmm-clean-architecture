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
    namespace = "io.tagd.arch"
}

pomBuilder {
    description.set("The technology agnostic architecture's definition")
}