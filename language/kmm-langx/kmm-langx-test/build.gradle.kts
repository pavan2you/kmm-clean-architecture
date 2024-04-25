plugins {
    id("io.tagd.kmm.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":language:kmm-langx"))
        }
    }
}

android {
    namespace = "io.tagd.langx.test"
}

pomBuilder {
    description.set("The technology agnostic language\'s extensions test definition")
}