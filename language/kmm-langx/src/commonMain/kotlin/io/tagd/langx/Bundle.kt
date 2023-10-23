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

    fun putParcelable(key: String?, value: Parcelable?)

    fun putSerializable(key: String?, value: Serializable?)

    fun putBoolean(key: String?, value: Boolean)

    fun putInt(key: String?, value: Int)

    fun putLong(key: String?, value: Long)

    fun putDouble(key: String?, value: Double)

    fun putString(key: String?, value: String?)

    fun size(): Int

    fun isEmpty(): Boolean

    fun getBoolean(key: String?): Boolean

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean

    fun getByte(key: String?): Byte

    fun getByte(key: String?, defaultValue: Byte): Byte

    fun getChar(key: String?): Char

    fun getChar(key: String?, defaultValue: Char): Char

    fun getShort(key: String?): Short

    fun getShort(key: String?, defaultValue: Short): Short

    fun getInt(key: String?): Int

    fun getInt(key: String?, defaultValue: Int): Int

    fun getLong(key: String?): Long

    fun getLong(key: String?, defaultValue: Long): Long

    fun getFloat(key: String?): Float

    fun getFloat(key: String?, defaultValue: Float): Float

    fun getDouble(key: String?): Double

    fun getDouble(key: String?, defaultValue: Double): Double

    fun getString(key: String?): String?

    fun getString(key: String?, defaultValue: String?): String?

    fun getCharSequence(key: String?): CharSequence?

    fun getCharSequence(key: String?, defaultValue: CharSequence?): CharSequence?

    fun getSerializable(key: String?): Serializable?

    fun <T : Parcelable?> getParcelable(key: String?): T?

    fun keySet(): Set<String?>?
}