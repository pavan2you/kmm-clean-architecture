plugins {
    id("io.tagd.kmm.library")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            api(project(":language:kmm-langx:kmm-langx-test"))
        }
        androidMain.dependencies {
            api(libs.gson)
        }
    }
}

android {
    namespace = "io.tagd.langx"
}

pomBuilder {
    description.set("The technology agnostic language\'s extensions")
}