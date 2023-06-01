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
include(":architecture:kmm-tagd-core")
include(":architecture:kmm-tagd-core:kmm-tagd-core-test")
include(":architecture:kmm-tagd-di")
include(":architecture:kmm-tagd-di:kmm-tagd-di-test")
include(":architecture:kmm-tagd-arch")
include(":architecture:kmm-tagd-arch:kmm-tagd-arch-test")
include(":architecture:kmm-tagd-android")
include(":architecture:kmm-tagd-android:kmm-tagd-android-test")

//Language Extensions
include(":language:kmm-langx")
include(":language:kmm-langx:kmm-langx-test")
include(":language:lang-kotlinx")
include(":language:lang-kotlinx:lang-kotlinx-test")
include(":language:lang-androidx")
include(":language:lang-androidx:lang-androidx-test")
