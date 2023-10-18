package io.tagd.langx

expect class Bundle {

    constructor()

    constructor(capacity: Int)

    constructor(b: Bundle)

    fun clear()

    fun remove(key: String)

    fun putAll(bundle: Bundle)

    fun putByte(key: String?, value: Byte)

    fun putChar(key: String?, value: Char)

    fun putShort(key: String?, value: Short)

    fun putFloat(key: String?, value: Float)

    fun putCharSequence(key: String?, value: CharSequence?)
}