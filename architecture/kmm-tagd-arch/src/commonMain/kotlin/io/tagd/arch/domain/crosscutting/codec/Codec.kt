package io.tagd.arch.domain.crosscutting.codec

import io.tagd.arch.domain.crosscutting.CrossCutting
import kotlin.reflect.KClass
import kotlin.reflect.KType

interface Codec<A : Any, B : Any> : CrossCutting {

    fun encode(plain: A, klass: KClass<B>? = null, type: KType? = null): B

    fun decode(encoded: B, klass: KClass<A>? = null, type: KType? = null): A
}