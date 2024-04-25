@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.collection.concurrent

actual class CopyOnWriteArrayList<E> : MutableList<E>, RandomAccess {

    // From List

    // From MutableCollection

    // From MutableList
    actual constructor() {
        TODO("Not yet implemented")
    }

    actual constructor(elements: Collection<E>) {
        TODO("Not yet implemented")
    }

    actual override val size: Int
        get() = TODO("Not yet implemented")

    actual override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun contains(element: @UnsafeVariance E): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun get(index: Int): E {
        TODO("Not yet implemented")
    }

    actual override fun indexOf(element: @UnsafeVariance E): Int {
        TODO("Not yet implemented")
    }

    actual override fun lastIndexOf(element: @UnsafeVariance E): Int {
        TODO("Not yet implemented")
    }

    actual override fun iterator(): MutableIterator<E> {
        TODO("Not yet implemented")
    }

    actual override fun add(element: E): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun remove(element: E): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun addAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun addAll(
        index: Int,
        elements: Collection<E>
    ): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun removeAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun retainAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun clear() {
    }

    actual override fun set(index: Int, element: E): E {
        TODO("Not yet implemented")
    }

    actual override fun add(index: Int, element: E) {
    }

    actual override fun removeAt(index: Int): E {
        TODO("Not yet implemented")
    }

    actual override fun listIterator(): MutableListIterator<E> {
        TODO("Not yet implemented")
    }

    actual override fun listIterator(index: Int): MutableListIterator<E> {
        TODO("Not yet implemented")
    }

    actual override fun subList(
        fromIndex: Int,
        toIndex: Int
    ): MutableList<E> {
        TODO("Not yet implemented")
    }

    actual fun asArray(array: Array<E>): Array<E> {
        TODO("Not yet implemented")
    }

}

actual fun <E> CopyOnWriteArrayList<E>.removeAllByFilter(
    predicate: (E) -> Boolean
) {
}