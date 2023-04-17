plugins {
    id("io.tagd.kotlin.library")
}

dependencies {
    api(libs.guava)

    testImplementation(project(":language:lang-kotlinx:lang-kotlinx-test"))
}

pomBuilder {
    description.set("The technology agnostic kotlin's extensions")
}
