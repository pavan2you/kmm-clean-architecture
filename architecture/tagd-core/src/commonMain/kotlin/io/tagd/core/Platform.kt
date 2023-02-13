package io.tagd.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform