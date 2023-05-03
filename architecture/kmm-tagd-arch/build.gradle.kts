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
                implementation(kotlin("test"))
                implementation(project(":architecture:kmm-tagd-arch:kmm-tagd-arch-test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("com.google.code.gson:gson:2.9.0")
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