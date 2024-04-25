plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":language:kmm-langx"))
        }
        commonTest.dependencies {
            api(kotlin("test"))
            api(project(":architecture:kmm-tagd-core:kmm-tagd-core-test"))
        }
    }
}

android {
    namespace = "io.tagd.core"
}

pomBuilder {
    description.set("The technology agnostic architecture's core traits definition")
}