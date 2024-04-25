@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.json

expect open class JSONException : Exception {

    constructor()

    constructor(message: String?)

    constructor(message: String?, cause: Throwable?)

    constructor(cause: Throwable?)
}