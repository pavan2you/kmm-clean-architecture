plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":architecture:kmm-tagd-core"))
            api(project(":language:kmm-langx:kmm-langx-test"))
        }
    }
}

android {
    namespace = "io.tagd.core.test"
}

pomBuilder {
    description.set("The technology agnostic architecture's core traits tests definition")
}