package io.tagd.langx

actual class UUID {

    private val _value = java.util.UUID.randomUUID().toString()

    actual val value: String
        get() = _value
}