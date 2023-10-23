package io.tagd.langx.json

expect open class JSONException : Exception {

    constructor()

    constructor(message: String?)

    constructor(message: String?, cause: Throwable?)

    constructor(cause: Throwable?)
}