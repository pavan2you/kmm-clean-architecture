package io.tagd.langx

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform