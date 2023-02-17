plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":language:kmm-langx"))
            }
        }
    }
}

android {
    namespace = "io.tagd.core"
}

pomBuilder {
    description.set("The technology agnostic architecture's core traits")
}