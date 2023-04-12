plugins {
    id("io.tagd.kotlin.library")
}

dependencies {
    api(libs.guava)
}

pomBuilder {
    description.set("The technology agnostic kotlin's extensions")
//    includeStartsWith.set(listOf(
//        "guava"
//    ))
}
