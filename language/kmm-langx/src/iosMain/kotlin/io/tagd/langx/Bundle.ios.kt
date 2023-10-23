package io.tagd.langx

actual class Bundle {

    actual fun clear() {
    }

    actual fun remove(key: String) {
    }

    actual fun putAll(bundle: Bundle) {
    }

    actual fun putByte(key: String?, value: Byte) {
    }

    actual fun putChar(key: String?, value: Char) {
    }

    actual fun putShort(key: String?, value: Short) {
    }

    actual fun putFloat(key: String?, value: Float) {
    }

    actual fun putCharSequence(key: String?, value: CharSequence?) {
    }

    actual constructor(capacity: Int) {
        TODO("Not yet implemented")
    }

    actual constructor(b: Bundle) {
        TODO("Not yet implemented")
    }

    actual constructor() {
        TODO("Not yet implemented")
    }

    actual fun putBoolean(key: String?, value: Boolean) {
    }

    actual fun putInt(key: String?, value: Int) {
    }

    actual fun putLong(key: String?, value: Long) {
    }

    actual fun putDouble(key: String?, value: Double) {
    }

    actual fun putString(key: String?, value: String?) {
    }

    actual fun size(): Int {
        TODO("Not yet implemented")
    }

    actual fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getBoolean(key: String?): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getByte(key: String?): Byte {
        TODO("Not yet implemented")
    }

    actual fun getByte(key: String?, defaultValue: Byte): Byte {
        TODO("Not yet implemented")
    }

    actual fun getChar(key: String?): Char {
        TODO("Not yet implemented")
    }

    actual fun getChar(key: String?, defaultValue: Char): Char {
        TODO("Not yet implemented")
    }

    actual fun getShort(key: String?): Short {
        TODO("Not yet implemented")
    }

    actual fun getShort(key: String?, defaultValue: Short): Short {
        TODO("Not yet implemented")
    }

    actual fun getInt(key: String?): Int {
        TODO("Not yet implemented")
    }

    actual fun getInt(key: String?, defaultValue: Int): Int {
        TODO("Not yet implemented")
    }

    actual fun getLong(key: String?): Long {
        TODO("Not yet implemented")
    }

    actual fun getLong(key: String?, defaultValue: Long): Long {
        TODO("Not yet implemented")
    }

    actual fun getFloat(key: String?): Float {
        TODO("Not yet implemented")
    }

    actual fun getFloat(key: String?, defaultValue: Float): Float {
        TODO("Not yet implemented")
    }

    actual fun getDouble(key: String?): Double {
        TODO("Not yet implemented")
    }

    actual fun getDouble(key: String?, defaultValue: Double): Double {
        TODO("Not yet implemented")
    }

    actual fun getString(key: String?): String? {
        TODO("Not yet implemented")
    }

    actual fun getString(key: String?, defaultValue: String?): String? {
        TODO("Not yet implemented")
    }

    actual fun getCharSequence(key: String?): CharSequence? {
        TODO("Not yet implemented")
    }

    actual fun getCharSequence(
        key: String?,
        defaultValue: CharSequence?
    ): CharSequence? {
        TODO("Not yet implemented")
    }

    actual fun putParcelable(key: String?, value: Parcelable?) {
    }

    actual fun putSerializable(key: String?, value: Serializable?) {
    }

    actual fun getSerializable(key: String?): Serializable? {
        TODO("Not yet implemented")
    }

    actual fun <T : Parcelable?> getParcelable(key: String?): T? {
        TODO("Not yet implemented")
    }

    actual fun keySet(): Set<String?>? {
        TODO("Not yet implemented")
    }
}