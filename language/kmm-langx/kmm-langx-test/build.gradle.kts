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
    namespace = "io.tagd.langx.test"
}

pomBuilder {
    description.set("The technology agnostic language\'s extensions test definition")
}