@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx

actual open class IllegalAccessException : Exception {

    actual constructor() : super()

    actual constructor(message: String?) : super(message)

    actual constructor(message: String?, cause: Throwable?) : super(message, cause)

    actual constructor(cause: Throwable?) : super(cause)
}

actual open class IllegalValueException : Exception {

    actual constructor() : super()

    actual constructor(message: String?) : super(message)

    actual constructor(message: String?, cause: Throwable?) : super(message, cause)

    actual constructor(cause: Throwable?) : super(cause)
}

actual open class IOException : Exception {

    actual constructor() : super()

    actual constructor(message: String?) : super(message)

    actual constructor(message: String?, cause: Throwable?) : super(message, cause)

    actual constructor(cause: Throwable?) : super(cause)
}