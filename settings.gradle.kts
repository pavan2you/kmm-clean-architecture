pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "kmm-clean-architecture"
include(":android")
include(":architecture")
include(":language")
include(":language:tagd-lang-langx")
include(":language:tagd-lang-kotlinx")
include(":language:tagd-lang-androidx")
include(":architecture:tagd-core")
include(":architecture:tagd-di")
include(":architecture:tagd-arch")
include(":architecture:tagd-droid")
include(":plugins")
include(":components")
