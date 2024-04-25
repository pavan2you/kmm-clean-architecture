plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":architecture:kmm-tagd-di"))
            api(project(":architecture:kmm-tagd-core:kmm-tagd-core-test"))
        }
        androidMain.dependencies {
            api(libs.mockito.kotlin)
        }
    }
}

android {
    namespace = "io.tagd.di.test"
}

pomBuilder {
    description.set("The technology agnostic architecture's di's test definition")
}