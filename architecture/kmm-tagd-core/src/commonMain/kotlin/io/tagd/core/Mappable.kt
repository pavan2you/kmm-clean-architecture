package io.tagd.core

interface Mappable {

    fun map(): HashMap<String, Any>
}

class MappableReference<T : Any>(val t: T) : Mappable {

    override fun map(): HashMap<String, Any> {
        return hashMapOf<String, Any>().apply {
            put("value", t)
        }
    }
}