@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.collection.concurrent

actual class ConcurrentHashMap<K, V> : MutableMap<K, V> {

    // From Map

    // From MutableMap
    actual constructor() {
        TODO("Not yet implemented")
    }

    actual constructor(initialCapacity: Int) {
        TODO("Not yet implemented")
    }

    actual constructor(initialCapacity: Int, loadFactor: Float) {
        TODO("Not yet implemented")
    }

    actual constructor(original: Map<out K, V>) {
        TODO("Not yet implemented")
    }

    actual override val size: Int
        get() = TODO("Not yet implemented")

    actual override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun containsKey(key: K): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun containsValue(value: @UnsafeVariance V): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun get(key: K): V? {
        TODO("Not yet implemented")
    }

    actual override fun put(key: K, value: V): V? {
        TODO("Not yet implemented")
    }

    actual override fun remove(key: K): V? {
        TODO("Not yet implemented")
    }

    actual override fun putAll(from: Map<out K, V>) {
    }

    actual override fun clear() {
    }

    actual override val keys: MutableSet<K>
        get() = TODO("Not yet implemented")
    actual override val values: MutableCollection<V>
        get() = TODO("Not yet implemented")
    actual override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = TODO("Not yet implemented")

    actual fun remove(key: K, value: V): Boolean {
        TODO("Not yet implemented")
    }

    actual fun getOrDefault(key: K, defaultValue: V): V {
        TODO("Not yet implemented")
    }

    actual fun putIfAbsent(key: K, value: V): V? {
        TODO("Not yet implemented")
    }

    actual fun replace(key: K, oldValue: V, newValue: V): Boolean {
        TODO("Not yet implemented")
    }

    actual fun replace(key: K, value: V): V? {
        TODO("Not yet implemented")
    }

}