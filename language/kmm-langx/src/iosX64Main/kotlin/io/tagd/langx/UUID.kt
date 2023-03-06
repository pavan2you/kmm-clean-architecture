package io.tagd.langx

import platform.Foundation.NSUUID

actual class UUID {

    private val _value = NSUUID().UUIDString()

    actual val value: String
        get() = _value
}