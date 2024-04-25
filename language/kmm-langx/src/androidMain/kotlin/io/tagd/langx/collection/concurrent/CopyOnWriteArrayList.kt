@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.collection.concurrent

actual class CopyOnWriteArrayList<E> : MutableList<E>, RandomAccess {

    private val delegate: java.util.concurrent.CopyOnWriteArrayList<E>

    actual constructor() {
        delegate = java.util.concurrent.CopyOnWriteArrayList<E>()
    }

    actual constructor(elements: Collection<E>) {
        delegate = java.util.concurrent.CopyOnWriteArrayList<E>(elements)
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

    actual override fun get(index: Int): E {
        return delegate.get(index)
    }

    actual override fun indexOf(element: @UnsafeVariance E): Int {
        return delegate.indexOf(element)
    }

    actual override fun lastIndexOf(element: @UnsafeVariance E): Int {
        return delegate.lastIndexOf(element)
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

    actual override fun addAll(
        index: Int,
        elements: Collection<E>
    ): Boolean {
        return delegate.addAll(index, elements)
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

    actual override fun set(index: Int, element: E): E {
        return delegate.set(index, element)
    }

    actual override fun add(index: Int, element: E) {
        delegate.add(index, element)
    }

    actual override fun removeAt(index: Int): E {
        return delegate.removeAt(index)
    }

    actual override fun listIterator(): MutableListIterator<E> {
        return delegate.listIterator()
    }

    actual override fun listIterator(index: Int): MutableListIterator<E> {
        return delegate.listIterator(index)
    }

    actual override fun subList(
        fromIndex: Int,
        toIndex: Int
    ): MutableList<E> {
        return delegate.subList(fromIndex, toIndex)
    }

    actual fun asArray(array: Array<E>): Array<E> {
        return delegate.toArray(array)
    }

}

actual fun <E> CopyOnWriteArrayList<E>.removeAllByFilter(predicate: (E) -> Boolean) {
    val filterFunction = this.filter {
        predicate.invoke(it)
    }
    removeAll(filterFunction.toSet())
}