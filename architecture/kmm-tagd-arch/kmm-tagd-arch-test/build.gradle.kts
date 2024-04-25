plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":architecture:kmm-tagd-arch"))
            api(project(":architecture:kmm-tagd-di:kmm-tagd-di-test"))
        }
    }
}

android {
    namespace = "io.tagd.arch.test"
}

pomBuilder {
    description.set("The technology agnostic architecture's definition tests mocks")
}