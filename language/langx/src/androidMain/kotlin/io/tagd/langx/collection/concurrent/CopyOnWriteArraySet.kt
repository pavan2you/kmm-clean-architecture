package io.tagd.langx.collection.concurrent

actual class CopyOnWriteArraySet<E> : MutableSet<E> {

    private val delegate: java.util.concurrent.CopyOnWriteArraySet<E>

    actual constructor() {
        delegate = java.util.concurrent.CopyOnWriteArraySet()
    }

    actual constructor(elements: Collection<E>) {
        delegate = java.util.concurrent.CopyOnWriteArraySet(elements)
    }

    actual override val size: Int
        get() = delegate.size

    actual override fun isEmpty(): Boolean {
        return delegate.isEmpty()
    }

    actual override fun contains(element: @UnsafeVariance E): Boolean {
        return delegate.contains(element)
    }

    actual override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean {
        return delegate.containsAll(elements)
    }

    actual override fun iterator(): MutableIterator<E> {
        return delegate.iterator()
    }

    actual override fun add(element: E): Boolean {
        return delegate.add(element)
    }

    actual override fun remove(element: E): Boolean {
        return delegate.remove(element)
    }

    actual override fun addAll(elements: Collection<E>): Boolean {
        return delegate.addAll(elements)
    }

    actual override fun removeAll(elements: Collection<E>): Boolean {
        return delegate.removeAll(elements)
    }

    actual override fun retainAll(elements: Collection<E>): Boolean {
        return delegate.retainAll(elements)
    }

    actual override fun clear() {
        delegate.clear()
    }

}