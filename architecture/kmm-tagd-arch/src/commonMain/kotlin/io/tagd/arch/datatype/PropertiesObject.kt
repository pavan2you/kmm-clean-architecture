package io.tagd.arch.datatype

import io.tagd.core.Copyable
import io.tagd.core.ValueProvider
import io.tagd.langx.collection.concurrent.ConcurrentHashMap

interface PropertiesObject : DataObjectable, Copyable {

    var properties: ConcurrentHashMap<String, Any?>?

    fun <T> set(name: String, value: T): PropertiesObject

    fun <T> set(vararg pairs: Pair<String, T?>): PropertiesObject

    fun <T> set(property: Property<T>): PropertiesObject

    fun <T> set(vararg property: Property<T>): PropertiesObject

    fun <T> set(name: String, value: ValueProvider<T>): PropertiesObject

    fun putAll(propertiesObject: PropertiesObject): PropertiesObject

    fun <T : Any?> value(name: String): T?

    fun <T> property(name: String): Property<T?>?

    fun removeProperty(name: String)

    fun copySuper(other: PropertiesObject)

    fun snapshot(): HashMap<String, Any>
}