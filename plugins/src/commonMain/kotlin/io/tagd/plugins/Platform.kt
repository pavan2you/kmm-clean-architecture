package io.tagd.plugins

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform