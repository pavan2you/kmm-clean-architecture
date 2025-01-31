package io.tagd.arch.crosscutting.codec

import kotlin.reflect.KClass
import kotlin.reflect.KType

interface EncoderDecoder<A : Any, B: Any> : Codec {

    fun encode(plain: A, klass: KClass<B>? = null, type: KType? = null): B

    fun decode(encoded: B, klass: KClass<A>? = null, type: KType? = null): A
}