plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":architecture:kmm-tagd-core"))
        }
        commonTest.dependencies {
            api(kotlin("test"))
            api(project(":architecture:kmm-tagd-di:kmm-tagd-di-test"))
        }
    }
}

android {
    namespace = "io.tagd.di"
}

pomBuilder {
    description.set("The technology agnostic architecture's di definition")
}