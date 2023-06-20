package io.tagd.langx

expect class UUID {

    val value: String

    constructor()

    constructor(value: String)

    companion object {
        fun fromString(name: String): UUID
    }
}