@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.json

actual open class JSONObject actual constructor() {

    /**
     * Creates a new {@code JSONObject} with name/value mappings from the JSON
     * string.
     *
     * @param json a JSON-encoded string containing an object.
     * @throws JSONException if the parse fails or doesn't yield a {@code
     *     JSONObject}.
     */
    @Throws(JSONException::class)
    actual constructor(json: String) : this()

    /**
     * Creates a new {@code JSONObject} by copying all name/value mappings from
     * the given map.
     *
     * @param copyFrom a map whose keys are of type {@link String} and whose
     *     values are of supported types.
     * @throws NullPointerException if any of the map's keys are null.
     */
    /* (accept a raw type for API compatibility) */
    actual constructor(copyFrom: Map<*, *>) : this()

    /**
     * Creates a new {@code JSONObject} by copying mappings for the listed names
     * from the given object. Names that aren't present in {@code copyFrom} will
     * be skipped.
     */
    actual constructor(
        copyFrom: JSONObject,
        names: Array<String?>?
    ) : this()


    /**
     * Returns the number of name/value mappings in this object.
     */
    actual open fun length(): Int {
        TODO("Not yet implemented")
    }

    /**
     * Maps {@code name} to {@code value}, clobbering any existing name/value
     * mapping with the same name.
     *
     * @return this object.
     */
    @Throws(JSONException::class)
    actual open fun put(
        name: String,
        value: Boolean
    ): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name.
     *
     * @param value a finite value. May not be [NaNs][Double.isNaN] or
     * [infinities][Double.isInfinite].
     * @return this object.
     */
    @Throws(JSONException::class)
    actual open fun put(
        name: String?,
        value: Double
    ): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name.
     *
     * @return this object.
     */
    @Throws(JSONException::class)
    actual open fun put(name: String?, value: Int): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name.
     *
     * @return this object.
     */
    @Throws(JSONException::class)
    actual open fun put(name: String?, value: Long): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Maps `name` to `value`, clobbering any existing name/value
     * mapping with the same name. If the value is `null`, any existing
     * mapping for `name` is removed.
     *
     * @param value a [JSONObject], [JSONArray], String, Boolean,
     * Integer, Long, Double, [.NULL], or `null`. May not be
     * [NaNs][Double.isNaN] or [     infinities][Double.isInfinite].
     * @return this object.
     */
    @Throws(JSONException::class)
    actual open fun put(name: String?, value: Any?): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Equivalent to `put(name, value)` when both parameters are non-null;
     * does nothing otherwise.
     */
    @Throws(JSONException::class)
    actual open fun putOpt(
        name: String?,
        value: Any?
    ): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Appends `value` to the array already mapped to `name`. If
     * this object has no mapping for `name`, this inserts a new mapping.
     * If the mapping exists but its value is not an array, the existing
     * and new values are inserted in order into a new array which is itself
     * mapped to `name`. In aggregate, this allows values to be added to a
     * mapping one at a time.
     *
     *
     *  Note that [.append] provides better semantics.
     * In particular, the mapping for `name` will **always** be a
     * [JSONArray]. Using `accumulate` will result in either a
     * [JSONArray] or a mapping whose type is the type of `value`
     * depending on the number of calls to it.
     *
     * @param value a [JSONObject], [JSONArray], String, Boolean,
     * Integer, Long, Double, [.NULL] or null.
     * May not be [     ][Double.isNaN] or [infinities][Double.isInfinite].
     */
    @Throws(JSONException::class)
    actual open fun accumulate(
        name: String?,
        value: Any?
    ): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Appends values to the array mapped to `name`. A new [JSONArray]
     * mapping for `name` will be inserted if no mapping exists. If the existing
     * mapping for `name` is not a [JSONArray], a [JSONException]
     * will be thrown.
     *
     * @throws JSONException if `name` is `null` or if the mapping for
     * `name` is non-null and is not a [JSONArray].
     */
    @Throws(JSONException::class)
    actual open fun append(
        name: String,
        value: Any?
    ): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Removes the named mapping if it exists; does nothing otherwise.
     *
     * @return the value previously mapped by `name`, or null if there was
     * no such mapping.
     */
    actual open fun remove(name: String?): Any? {
        TODO("Not yet implemented")
    }

    /**
     * Returns true if this object has no mapping for `name` or if it has
     * a mapping whose value is [.NULL].
     */
    actual open fun isNull(name: String?): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns true if this object has a mapping for `name`. The mapping
     * may be [.NULL].
     */
    actual open fun has(name: String?): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name`, or throws if no such mapping exists.
     *
     * @throws JSONException if no such mapping exists.
     */
    @Throws(JSONException::class)
    actual open operator fun get(name: String): Any {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name`, or null if no such mapping
     * exists.
     */
    actual open fun opt(name: String?): Any {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a boolean or
     * can be coerced to a boolean, or throws otherwise.
     *
     * @throws JSONException if the mapping doesn't exist or cannot be coerced
     * to a boolean.
     */
    @Throws(JSONException::class)
    actual open fun getBoolean(name: String): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a boolean or
     * can be coerced to a boolean, or false otherwise.
     */
    actual open fun optBoolean(name: String?): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a boolean or
     * can be coerced to a boolean, or `fallback` otherwise.
     */
    actual open fun optBoolean(name: String?, fallback: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a double or
     * can be coerced to a double, or throws otherwise.
     *
     * @throws JSONException if the mapping doesn't exist or cannot be coerced
     * to a double.
     */
    @Throws(JSONException::class)
    actual open fun getDouble(name: String): Double {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a double or
     * can be coerced to a double, or `NaN` otherwise.
     */
    actual open fun optDouble(name: String?): Double {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a double or
     * can be coerced to a double, or `fallback` otherwise.
     */
    actual open fun optDouble(name: String?, fallback: Double): Double {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is an int or
     * can be coerced to an int, or throws otherwise.
     *
     * @throws JSONException if the mapping doesn't exist or cannot be coerced
     * to an int.
     */
    @Throws(JSONException::class)
    actual open fun getInt(name: String): Int {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is an int or
     * can be coerced to an int, or 0 otherwise.
     */
    actual open fun optInt(name: String?): Int {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is an int or
     * can be coerced to an int, or `fallback` otherwise.
     */
    actual open fun optInt(name: String?, fallback: Int): Int {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a long or
     * can be coerced to a long, or throws otherwise.
     * Note that JSON represents numbers as doubles,
     * so this is [lossy](#lossy); use strings to transfer numbers via JSON.
     *
     * @throws JSONException if the mapping doesn't exist or cannot be coerced
     * to a long.
     */
    @Throws(JSONException::class)
    actual open fun getLong(name: String): Long {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a long or
     * can be coerced to a long, or 0 otherwise. Note that JSON represents numbers as doubles,
     * so this is [lossy](#lossy); use strings to transfer numbers via JSON.
     */
    actual open fun optLong(name: String?): Long {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a long or
     * can be coerced to a long, or `fallback` otherwise. Note that JSON represents
     * numbers as doubles, so this is [lossy](#lossy); use strings to transfer
     * numbers via JSON.
     */
    actual open fun optLong(name: String?, fallback: Long): Long {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists, coercing it if
     * necessary, or throws if no such mapping exists.
     *
     * @throws JSONException if no such mapping exists.
     */
    @Throws(JSONException::class)
    actual open fun getString(name: String): String? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists, coercing it if
     * necessary, or the empty string if no such mapping exists.
     */
    actual open fun optString(name: String?): String? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists, coercing it if
     * necessary, or `fallback` if no such mapping exists.
     */
    actual open fun optString(name: String?, fallback: String?): String? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a `JSONArray`, or throws otherwise.
     *
     * @throws JSONException if the mapping doesn't exist or is not a `JSONArray`.
     */
    @Throws(JSONException::class)
    actual open fun getJSONArray(name: String): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a `JSONArray`, or null otherwise.
     */
    actual open fun optJSONArray(name: String?): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a `JSONObject`, or throws otherwise.
     *
     * @throws JSONException if the mapping doesn't exist or is not a `JSONObject`.
     */
    @Throws(JSONException::class)
    actual open fun getJSONObject(name: String): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Returns the value mapped by `name` if it exists and is a `JSONObject`, or null otherwise.
     */
    actual open fun optJSONObject(name: String?): JSONObject? {
        TODO("Not yet implemented")
    }

    /**
     * Returns an array with the values corresponding to `names`. The
     * array contains null for names that aren't mapped. This method returns
     * null if `names` is either null or empty.
     */
    @Throws(JSONException::class)
    actual open fun toJSONArray(names: JSONArray?): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Returns an iterator of the `String` names in this object. The
     * returned iterator supports [remove][Iterator.remove], which will
     * remove the corresponding mapping from this object. If this object is
     * modified after the iterator is returned, the iterator's behavior is
     * undefined. The order of the keys is undefined.
     */
    actual open fun keys(): Iterator<String?>? {
        TODO("Not yet implemented")
    }

    /**
     * Returns an array containing the string names in this object. This method
     * returns null if this object contains no mappings.
     */
    actual open fun names(): JSONArray? {
        TODO("Not yet implemented")
    }

    /**
     * Encodes this object as a human readable JSON string for debugging, such
     * as:
     * <pre>
     * {
     *     "query": "Pizza",
     *     "locations": [
     *         94043,
     *         90210
     *     ]
     * }</pre>
     *
     * @param indentSpaces the number of spaces to indent for each level of
     *     nesting.
     */
    @Throws(JSONException::class)
    actual open fun toString(indentSpaces: Int): String? {
        TODO("Not yet implemented")
    }
}