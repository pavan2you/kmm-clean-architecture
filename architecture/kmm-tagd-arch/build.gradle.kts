plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":architecture:kmm-tagd-di"))
        }
        commonTest.dependencies {
            api(kotlin("test"))
            api(project(":architecture:kmm-tagd-arch:kmm-tagd-arch-test"))
        }
        androidMain.dependencies {
            api(libs.gson)
        }
    }
}

android {
    namespace = "io.tagd.arch"
}

pomBuilder {
    description.set("The technology agnostic architecture's definition")
}