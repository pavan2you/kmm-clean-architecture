@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.collection.concurrent

actual class CopyOnWriteArraySet<E> : MutableSet<E> {
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

    actual override fun removeAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun retainAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    actual override fun clear() {
    }

}