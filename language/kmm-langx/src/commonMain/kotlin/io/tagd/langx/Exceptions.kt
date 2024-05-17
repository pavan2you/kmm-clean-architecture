@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx

expect open class IllegalAccessException : Exception {

    constructor()

    constructor(message: String?)

    constructor(message: String?, cause: Throwable?)

    constructor(cause: Throwable?)
}

expect open class IllegalValueException : Exception {

    constructor()

    constructor(message: String?)

    constructor(message: String?, cause: Throwable?)

    constructor(cause: Throwable?)
}

expect open class IOException : Exception {

    constructor()

    constructor(message: String?)

    constructor(message: String?, cause: Throwable?)

    constructor(cause: Throwable?)
}