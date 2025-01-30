package io.tagd.arch.domain.crosscutting.converter

import io.tagd.core.CrossCutting

class ConvertException(message: String, cause: Throwable) : Exception(message, cause)

interface Converter<A, B> : CrossCutting {

    @Throws(ConvertException::class)
    fun convert(value: A) : B

    override fun release() {}
}