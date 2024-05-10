package io.tagd.arch.datatype

import io.tagd.core.ValueProvider
import io.tagd.langx.collection.concurrent.ConcurrentHashMap

open class InMemoryPropertiesObject : DataObject(), PropertiesObject {

    final override var properties: ConcurrentHashMap<String, Any?>? = null

    init {
        this.initialize()
    }

    override fun initialize() {
        super.initialize()
        properties = ConcurrentHashMap()
    }

    override fun <T> set(name: String, value: T): PropertiesObject {
        properties?.set(name, value)
        return this
    }

    override fun <T> set(vararg pairs: Pair<String, T?>): PropertiesObject {
        properties?.putAll(pairs)
        return this
    }

    override fun <T> set(property: Property<T>): PropertiesObject {
        properties?.set(property.name, property.value)
        return this
    }

    override fun <T> set(vararg property: Property<T>): PropertiesObject {
        properties?.putAll(property.map { it.name to it.value })
        return this
    }

    override fun <T> set(name: String, value: ValueProvider<T>): PropertiesObject {
        properties?.set(name, value)
        return this
    }

    override fun putAll(propertiesObject: PropertiesObject): PropertiesObject {
        propertiesObject.properties?.let {
            properties?.putAll(it)
        }
        return this
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> value(name: String): T? {
        val value = properties?.get(name)

        return if (value is ValueProvider<*>?) {
            value?.value() as? T?
        } else {
            value as T?
        }
    }

    override fun <T> property(name: String): Property<T?>? {
        val value = value<T>(name)
        return Property(name, value)
    }

    override fun removeProperty(name: String) {
        properties?.remove(name)
    }

    override fun copySuper(other: PropertiesObject) {
        other.initialize()
        other.putAll(other)
    }

    override fun snapshot(): HashMap<String, Any> {
        val snapshot = hashMapOf<String, Any>()
        properties?.forEach { property ->
            putInSnapshot(snapshot, property)
        }
        return snapshot
    }

    protected open fun putInSnapshot(
        snapshot: HashMap<String, Any>,
        property: Map.Entry<String, Any?>
    ) {

        when (property.value) {
            is ValueProvider<*>? -> {
                val dynamicValue = (property.value as? ValueProvider<*>)?.value()
                dynamicValue?.let { propertyValue ->
                    snapshot.put(property.key, propertyValue)
                }
            }

            else -> {
                property.value?.let { propertyValue ->
                    snapshot[property.key] = propertyValue
                }
            }
        }
    }

    override fun release() {
        super.release()
        properties?.clear()
        properties = null
    }
}