package io.tagd.core

interface Validatable {

    @Throws(ValidateException::class)
    fun validate()
}

class ValidateException(val validatable: Validatable, message: String, cause: Throwable) :
    Exception(message, cause)
