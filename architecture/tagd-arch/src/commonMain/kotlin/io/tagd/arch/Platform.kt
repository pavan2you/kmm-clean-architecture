package io.tagd.arch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform