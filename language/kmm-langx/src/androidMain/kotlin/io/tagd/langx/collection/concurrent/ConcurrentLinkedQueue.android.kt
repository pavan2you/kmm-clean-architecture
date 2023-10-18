package io.tagd.langx.collection.concurrent

actual class ConcurrentLinkedQueue<E> : MutableCollection<E> {

    private val delegate: java.util.concurrent.ConcurrentLinkedQueue<E>

    actual constructor() {
        delegate = java.util.concurrent.ConcurrentLinkedQueue<E>()
    }

    actual constructor(elements: Collection<E>) {
        delegate = java.util.concurrent.ConcurrentLinkedQueue<E>(elements)
    }

    actual fun offer(e: E): Boolean {
        return delegate.offer(e)
    }

    actual fun poll(): E? {
        return delegate.poll()
    }

    actual fun peek(): E? {
        return delegate.peek()
    }

    actual fun remove(): E {
        return delegate.remove()
    }

    actual fun element(): E {
        return delegate.element()
    }

    override val size: Int
        get() = delegate.size

    override fun clear() {
        delegate.clear()
    }

    override fun addAll(elements: Collection<E>): Boolean {
        return delegate.addAll(elements)
    }

    override fun add(element: E): Boolean {
        return delegate.add(element)
    }

    override fun isEmpty(): Boolean {
        return delegate.isEmpty()
    }

    override fun iterator(): MutableIterator<E> {
        return delegate.iterator()
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return delegate.retainAll(elements)
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        return delegate.removeAll(elements)
    }

    override fun remove(element: E): Boolean {
        return delegate.remove(element)
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return delegate.containsAll(elements)
    }

    override fun contains(element: E): Boolean {
        return delegate.contains(element)
    }

    actual fun asArray(array: Array<E>): Array<E> {
        return delegate.toArray(array)
    }
}

actual fun <E> ConcurrentLinkedQueue<E>.removeAllByFilter(predicate: (E) -> Boolean) {
    val filterFunction = this.filter {
        predicate.invoke(it)
    }
    removeAll(filterFunction.toSet())
}