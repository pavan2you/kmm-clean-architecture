@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.datatype

import platform.Foundation.NSUUID

actual class UUID {

    private val _value = NSUUID().UUIDString()

    actual val value: String
        get() = _value

    actual constructor() {
        TODO("Not yet implemented")
    }

    actual constructor(value: String) {
        TODO("Not yet implemented")
    }

    actual companion object {
        actual fun fromString(name: String): UUID {
            TODO("Not yet implemented")
        }
    }
}