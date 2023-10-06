package io.tagd.core

import io.tagd.langx.IllegalAccessException

interface Validatable {

    @Throws(ValidateException::class)
    fun validate(): Boolean
}

class ValidateException(
    val validatable: Validatable,
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)

interface Validator : Service {

    @Throws(ValidateException::class)
    fun validate(validatable: Validatable): Boolean {
        return validatable.validate()
    }
}

open class ValidatableReference<T>(val reference: T) : Validatable {

    override fun validate(): Boolean {
        throw IllegalAccessException("Use Validator to validate")
    }
}