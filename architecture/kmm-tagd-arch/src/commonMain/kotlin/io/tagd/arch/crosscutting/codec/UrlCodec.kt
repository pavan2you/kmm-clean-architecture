package io.tagd.arch.crosscutting.codec

import io.tagd.core.CrossCutting

interface UrlCodec : CrossCutting {

    fun encode(data: String, charset: String): String

    fun decode(data: String, charset: String): String
}