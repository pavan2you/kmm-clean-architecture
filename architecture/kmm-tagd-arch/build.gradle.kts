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
        val commonTest by getting {
            dependencies {
                api(kotlin("test"))
                api(project(":architecture:kmm-tagd-arch:kmm-tagd-arch-test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.gson)
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