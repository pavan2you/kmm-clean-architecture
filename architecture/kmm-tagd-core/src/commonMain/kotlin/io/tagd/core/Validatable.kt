package io.tagd.core

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