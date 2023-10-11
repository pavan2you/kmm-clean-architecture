package io.tagd.core

import io.tagd.langx.IllegalAccessException

interface Validatable {

    @Throws(ValidateException::class)
    fun validate()

    fun valid(): Boolean {
        return try {
            validate()
            true
        } catch (e: ValidateException) {
            false
        }
    }
}

class ValidateException(
    val validatable: Validatable,
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause)

interface Validator : Service {

    @Throws(ValidateException::class)
    fun validate(validatable: Validatable) {
        return validatable.validate()
    }

    fun valid(validatable: Validatable): Boolean {
        return validatable.valid()
    }
}

open class ValidatableReference<T>(val reference: T) : Validatable {

    override fun validate() {
        throw IllegalAccessException("Use Validator to validate")
    }

    override fun valid(): Boolean {
        throw IllegalAccessException("Use Validator to validate")
    }
}