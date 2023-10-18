plugins {
    id("io.tagd.kmm.library")
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        val commonTest by getting {
            dependencies {
                api(project(":language:kmm-langx:kmm-langx-test"))
            }
        }
    }
}

android {
    namespace = "io.tagd.langx"
}

pomBuilder {
    description.set("The technology agnostic language\'s extensions")
}