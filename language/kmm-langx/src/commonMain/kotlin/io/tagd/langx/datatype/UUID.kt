@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.datatype

expect class UUID {

    val value: String

    constructor()

    constructor(value: String)

    companion object {
        fun fromString(name: String): UUID
    }
}