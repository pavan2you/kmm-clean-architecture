package io.tagd.langx.collection.concurrent

actual class ConcurrentLinkedQueue<E> : MutableCollection<E> {

    actual fun offer(e: E): Boolean {
        TODO("Not yet implemented")
    }

    actual fun poll(): E? {
        TODO("Not yet implemented")
    }

    actual fun peek(): E? {
        TODO("Not yet implemented")
    }

    /**
     * Retrieves and removes the head of this queue.  This method differs
     * from [poll][.poll] only in that it throws an exception if this
     * queue is empty.
     *
     *
     * This implementation returns the result of `poll`
     * unless the queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    actual fun remove(): E {
        TODO("Not yet implemented")
    }

    /**
     * Retrieves, but does not remove, the head of this queue.  This method
     * differs from [peek][.peek] only in that it throws an exception if
     * this queue is empty.
     *
     *
     * This implementation returns the result of `peek`
     * unless the queue is empty.
     *
     * @return the head of this queue
     * @throws NoSuchElementException if this queue is empty
     */
    actual fun element(): E {
        TODO("Not yet implemented")
    }

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(element: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<E> {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(element: E): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(element: E): Boolean {
        TODO("Not yet implemented")
    }

    actual fun asArray(array: Array<E>): Array<E> {
        return array //todo
    }

    actual constructor(elements: Collection<E>) {
        TODO("Not yet implemented")
    }

    actual constructor() {
        TODO("Not yet implemented")
    }
}

actual fun <E> ConcurrentLinkedQueue<E>.removeAllByFilter(predicate: (E) -> Boolean) {
    val filterFunction = this.filter {
        predicate.invoke(it)
    }
    removeAll(filterFunction.toSet())
}