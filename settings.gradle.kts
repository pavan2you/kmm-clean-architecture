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
include(":applications:the101:android")

include(":architecture")
include(":architecture:kmm-tagd-core")
include(":architecture:kmm-tagd-di")
include(":architecture:kmm-tagd-arch")
include(":architecture:kmm-tagd-android")

include(":language")
include(":language:kmm-lang-langx")
include(":language:lang-kotlinx")
include(":language:lang-androidx")

include(":plugins")

include(":components")
