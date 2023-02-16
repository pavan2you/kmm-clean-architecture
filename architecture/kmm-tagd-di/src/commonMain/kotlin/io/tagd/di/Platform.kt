package io.tagd.di

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform