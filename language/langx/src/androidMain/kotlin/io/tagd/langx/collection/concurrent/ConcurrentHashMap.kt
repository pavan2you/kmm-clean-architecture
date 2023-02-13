package io.tagd.langx.collection.concurrent

actual typealias ConcurrentHashMap<K, V> = java.util.concurrent.ConcurrentHashMap<K, V>

//actual class ConcurrentHashMap<K, V> : MutableMap<K, V> {
//
//    private val delegate: java.util.concurrent.ConcurrentHashMap<K, V>
//
//    actual constructor() {
//        delegate = java.util.concurrent.ConcurrentHashMap<K, V>()
//    }
//
//    actual constructor(initialCapacity: Int) {
//        delegate = java.util.concurrent.ConcurrentHashMap<K, V>(initialCapacity)
//    }
//
//    actual constructor(initialCapacity: Int, loadFactor: Float) {
//        delegate = java.util.concurrent.ConcurrentHashMap<K, V>(initialCapacity, loadFactor)
//    }
//
//    actual constructor(original: Map<out K, V>) {
//        delegate = java.util.concurrent.ConcurrentHashMap<K, V>(original)
//    }
//
//    actual override val size: Int
//        get() = delegate.size
//
//    actual override fun isEmpty(): Boolean {
//        return delegate.isEmpty()
//    }
//
//    actual override fun containsKey(key: K): Boolean {
//        return delegate.containsKey(key)
//    }
//
//    actual override fun containsValue(value: @UnsafeVariance V): Boolean {
//        return delegate.containsValue(value)
//
//    }
//
//    actual override fun get(key: K): V? {
//        return delegate[key]
//    }
//
//    actual override fun put(key: K, value: V): V? {
//        return delegate.put(key!!, value!!)
//    }
//
//    actual override fun remove(key: K): V? {
//        return delegate.remove(key)
//    }
//
//    actual override fun putAll(from: Map<out K, V>) {
//        delegate.putAll(from)
//    }
//
//    actual override fun clear() {
//        delegate.clear()
//    }
//
//    actual override val keys: MutableSet<K>
//        get() = delegate.keys
//    actual override val values: MutableCollection<V>
//        get() = delegate.values
//    actual override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
//        get() = delegate.entries
//
//}