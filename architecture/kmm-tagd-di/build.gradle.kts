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
        val commonTest by getting {
            dependencies {
                api(kotlin("test"))
                api(project(":architecture:kmm-tagd-di:kmm-tagd-di-test"))
            }
        }
    }
}

android {
    namespace = "io.tagd.di"
}

pomBuilder {
    description.set("The technology agnostic architecture's di definition")
}