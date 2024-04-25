@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.datatype

actual class UUID {

    private val _value: String

    actual val value: String
        get() = _value

    actual constructor() {
        _value = java.util.UUID.randomUUID().toString()
    }

    actual constructor(value: String) {
        _value = value
    }

    actual companion object {
        actual fun fromString(name: String): UUID {
            val uuid = java.util.UUID.fromString(name).toString()
            return UUID(uuid)
        }
    }
}