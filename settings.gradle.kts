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
    }
}

rootProject.name = "kmm-clean-architecture"
include(":android")
include(":architecture")
include(":language")
include(":language:langx")
include(":language:kotlinx")
include(":language:androidx")
include(":architecture:tagd-core")
include(":architecture:tagd-di")
include(":architecture:tagd-arch")
include(":architecture:tagd-droid")
include(":plugins")
include(":components")
