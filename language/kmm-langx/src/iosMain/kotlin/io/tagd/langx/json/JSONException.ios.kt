package io.tagd.langx.json

actual open class JSONException : Exception {

    actual constructor() : super()

    actual constructor(message: String?) : super(message)

    actual constructor(message: String?, cause: Throwable?) : super(message, cause)

    actual constructor(cause: Throwable?) : super(cause)
}