plugins {
    id("io.tagd.kotlin.library")
}

dependencies {
    api(project(":language:lang-kotlinx"))
    api(libs.guava)
}

pomBuilder {
    description.set("The technology agnostic kotlin's extensions")
//    includeStartsWith.set(listOf(
//        "guava"
//    ))
}