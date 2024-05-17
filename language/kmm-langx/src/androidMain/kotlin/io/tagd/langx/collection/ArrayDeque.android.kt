@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.collection

import java.util.ArrayDeque

actual class ArrayDeque<E> actual constructor() : Deque<E> {

    private val delegate: ArrayDeque<E> = ArrayDeque()

    actual constructor(elements: Collection<E>) : this() {
        delegate.addAll(elements)
    }

    override val size: Int
        get() = delegate.size

    override fun contains(element: E): Boolean {
        return delegate.contains(element)
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return delegate.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return delegate.isEmpty()
    }

    override fun addFirst(e: E) {
        delegate.addFirst(e)
    }

    override fun addLast(e: E) {
        delegate.addLast(e)
    }

    override fun offerFirst(e: E): Boolean {
        return delegate.offerFirst(e)
    }

    override fun offerLast(e: E): Boolean {
        return delegate.offerLast(e)
    }

    override fun removeFirst(): E {
        return delegate.removeFirst()
    }

    override fun removeLast(): E {
        return delegate.removeLast()
    }

    override fun pollFirst(): E? {
        return delegate.pollFirst()
    }

    override fun pollLast(): E? {
        return delegate.pollLast()
    }

    override fun getFirst(): E? {
        return delegate.first
    }

    override fun getLast(): E? {
        return delegate.last
    }

    override fun peekFirst(): E? {
        return delegate.peekFirst()
    }

    override fun peekLast(): E? {
        return delegate.peekLast()
    }

    override fun removeFirstOccurrence(o: E): Boolean {
        return delegate.removeFirstOccurrence(o)
    }

    override fun removeLastOccurrence(o: E): Boolean {
        return delegate.removeLastOccurrence(o)
    }

    override fun add(e: E): Boolean {
        return delegate.add(e)
    }

    override fun offer(e: E): Boolean {
        return delegate.offer(e)
    }

    override fun remove(): E {
        return delegate.remove()
    }

    override fun poll(): E? {
        return delegate.poll()
    }

    override fun element(): E {
        return delegate.element()
    }

    override fun peek(): E? {
        return delegate.peek()
    }

    override fun addAll(c: Collection<E>): Boolean {
        return delegate.addAll(c)
    }

    override fun push(e: E) {
        delegate.push(e)
    }

    override fun pop(): E {
        return delegate.pop()
    }

    override fun remove(o: E): Boolean {
        return delegate.remove(o)
    }

    override fun iterator(): Iterator<E> {
        return delegate.iterator()
    }

    override fun descendingIterator(): Iterator<E>? {
        return delegate.descendingIterator()
    }

    override fun clear() {
        delegate.clear()
    }
}