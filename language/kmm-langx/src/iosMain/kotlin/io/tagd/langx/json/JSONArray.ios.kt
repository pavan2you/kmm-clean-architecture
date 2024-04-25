@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.json

actual open class JSONArray actual constructor() {

    /**
     * Creates a new {@code JSONArray} by copying all values from the given
     * collection.
     *
     * @param copyFrom a collection whose values are of supported types.
     *     Unsupported values are not permitted and will yield an array in an
     *     inconsistent state.
     */
    /* Accept a raw type for API compatibility */
    actual constructor(copyFrom: Collection<*>?) : this()

    /**
     * Creates a new {@code JSONArray} with values from the JSON string.
     *
     * @param json a JSON-encoded string containing an array.
     * @throws JSONException if the parse fails or doesn't yield a {@code
     *     JSONArray}.
     */
    @Throws(JSONException::class)
    actual constructor(json: String?) : this()


    /**
     * Creates a new `JSONArray` with values from the given primitive array.
     */
    @Throws(JSONException::class)
    actual constructor(array: Any) : this()


    /**
     * Returns the number of values in this array.
     */
    actual open fun length(): Int {
        TODO("Not yet implemented")
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @return this array.
     */
    actual open fun put(value: Boolean): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @param value a finite value. May not be [NaNs][Double.isNaN] or
     * [infinities][Double.isInfinite].
     * @return this array.
     */
    @Throws(JSONException::class)
    actual open fun put(value: Double): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @return this array.
     */
    actual open fun put(value: Int): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @return this array.
     */
    actual open fun put(value: Long): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Appends `value` to the end of this array.
     *
     * @param value a [JSONObject], [JSONArray], String, Boolean,
     * Integer, Long, Double, [JSONObject.NULL], or `null`. May
     * not be [NaNs][Double.isNaN] or [     infinities][Double.isInfinite].
     * Unsupported values are not permitted and will cause the
     * array to be in an inconsistent state.
     * @return this array.
     */
    actual open fun put(value: Any?): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @return this array.
     */
    @Throws(JSONException::class)
    actual open fun put(index: Int, value: Boolean): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @param value a finite value. May not be [NaNs][Double.isNaN] or
     * [infinities][Double.isInfinite].
     * @return this array.
     */
    @Throws(JSONException::class)
    actual open fun put(index: Int, value: Double): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @return this array.
     */
    @Throws(JSONException::class)
    actual open fun put(index: Int, value: Int): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @return this array.
     */
    @Throws(JSONException::class)
    actual open fun put(index: Int, value: Long): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Sets the value at `index` to `value`, null padding this array
     * to the required length if necessary. If a value already exists at `index`, it will be replaced.
     *
     * @param value a [JSONObject], [JSONArray], String, Boolean,
     * Integer, Long, Double, [JSONObject.NULL], or `null`. May
     * not be [NaNs][Double.isNaN] or [     infinities][Double.isInfinite].
     * @return this array.
     */
    @Throws(JSONException::class)
    actual open fun put(index: Int, value: Any?): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Returns true if this array has no value at `index`, or if its value
     * is the `null` reference or [JSONObject.NULL].
     */
    actual open fun isNull(index: Int): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index`.
     *
     * @throws JSONException if this array has no value at `index`, or if
     * that value is the `null` reference. This method returns
     * normally if the value is `JSONObject#NULL`.
     */
    @Throws(JSONException::class)
    actual open operator fun get(index: Int): Any {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index`, or null if the array has no value
     * at `index`.
     */
    actual open fun opt(index: Int): Any? {
        TODO("Not yet implemented")
    }

    /**
     * Removes and returns the value at `index`, or null if the array has no value
     * at `index`.
     */
    actual open fun remove(index: Int): Any? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a boolean or can
     * be coerced to a boolean.
     *
     * @throws JSONException if the value at `index` doesn't exist or
     * cannot be coerced to a boolean.
     */
    @Throws(JSONException::class)
    actual open fun getBoolean(index: Int): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a boolean or can
     * be coerced to a boolean. Returns false otherwise.
     */
    actual open fun optBoolean(index: Int): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a boolean or can
     * be coerced to a boolean. Returns `fallback` otherwise.
     */
    actual open fun optBoolean(index: Int, fallback: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a double or can
     * be coerced to a double.
     *
     * @throws JSONException if the value at `index` doesn't exist or
     * cannot be coerced to a double.
     */
    @Throws(JSONException::class)
    actual open fun getDouble(index: Int): Double {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a double or can
     * be coerced to a double. Returns `NaN` otherwise.
     */
    actual open fun optDouble(index: Int): Double {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a double or can
     * be coerced to a double. Returns `fallback` otherwise.
     */
    actual open fun optDouble(index: Int, fallback: Double): Double {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is an int or
     * can be coerced to an int.
     *
     * @throws JSONException if the value at `index` doesn't exist or
     * cannot be coerced to a int.
     */
    @Throws(JSONException::class)
    actual open fun getInt(index: Int): Int {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is an int or
     * can be coerced to an int. Returns 0 otherwise.
     */
    actual open fun optInt(index: Int): Int {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is an int or
     * can be coerced to an int. Returns `fallback` otherwise.
     */
    actual open fun optInt(index: Int, fallback: Int): Int {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a long or
     * can be coerced to a long.
     *
     * @throws JSONException if the value at `index` doesn't exist or
     * cannot be coerced to a long.
     */
    @Throws(JSONException::class)
    actual open fun getLong(index: Int): Long {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a long or
     * can be coerced to a long. Returns 0 otherwise.
     */
    actual open fun optLong(index: Int): Long {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a long or
     * can be coerced to a long. Returns `fallback` otherwise.
     */
    actual open fun optLong(index: Int, fallback: Long): Long {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists, coercing it if
     * necessary.
     *
     * @throws JSONException if no such value exists.
     */
    @Throws(JSONException::class)
    actual open fun getString(index: Int): String? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists, coercing it if
     * necessary. Returns the empty string if no such value exists.
     */
    actual open fun optString(index: Int): String? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists, coercing it if
     * necessary. Returns `fallback` if no such value exists.
     */
    actual open fun optString(index: Int, fallback: String?): String? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a `JSONArray`.
     *
     * @throws JSONException if the value doesn't exist or is not a `JSONArray`.
     */
    @Throws(JSONException::class)
    actual open fun getJSONArray(index: Int): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a `JSONArray`. Returns null otherwise.
     */
    actual open fun optJSONArray(index: Int): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a `JSONObject`.
     *
     * @throws JSONException if the value doesn't exist or is not a `JSONObject`.
     */
    @Throws(JSONException::class)
    actual open fun getJSONObject(index: Int): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value at `index` if it exists and is a `JSONObject`. Returns null otherwise.
     */
    actual open fun optJSONObject(index: Int): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Returns a new object whose values are the values in this array, and whose
     * names are the values in `names`. Names and values are paired up by
     * index from 0 through to the shorter array's length. Names that are not
     * strings will be coerced to strings. This method returns null if either
     * array is empty.
     */
    @Throws(JSONException::class)
    actual open fun toJSONObject(names: JSONArray): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Returns a new string by alternating this array's values with `separator`.
     * This array's string values are quoted and have their special
     * characters escaped. For example, the array containing the strings '12"
     * pizza', 'taco' and 'soda' joined on '+' returns this:
     * <pre>"12\" pizza"+"taco"+"soda"</pre>
     */
    @Throws(JSONException::class)
    actual open fun join(separator: String?): String? {
        TODO("Not yet implemented")
    }

    /**
     * Encodes this array as a human readable JSON string for debugging, such
     * as:
     * <pre>
     * [
     * 94043,
     * 90210
     * ]</pre>
     *
     * @param indentSpaces the number of spaces to indent for each level of
     * nesting.
     */
    @Throws(JSONException::class)
    actual open fun toString(indentSpaces: Int): String? {
        TODO("Not yet implemented")
    }
}