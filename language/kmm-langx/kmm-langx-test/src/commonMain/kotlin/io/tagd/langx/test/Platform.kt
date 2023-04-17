package io.tagd.langx.test

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform