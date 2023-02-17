pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
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

//Applications
include(":applications:the101:android")

//Architecture
include(":architecture")
include(":architecture:kmm-tagd-core")
include(":architecture:kmm-tagd-di")
include(":architecture:kmm-tagd-arch")
include(":architecture:kmm-tagd-android")

//Language Extentions
include(":language")
include(":language:kmm-langx")
include(":language:lang-kotlinx")
include(":language:lang-androidx")

//Plugins
include(":plugins")

//Components
include(":components")
