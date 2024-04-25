@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.collection.concurrent

expect class ConcurrentLinkedQueue<E> : MutableCollection<E> {

    constructor()

    constructor(elements: Collection<E>)

    fun offer(e: E): Boolean

    fun poll(): E?

    fun peek(): E?

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
    fun remove(): E

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
    fun element(): E

    fun asArray(array: Array<E>): Array<E>
}

expect fun <E> ConcurrentLinkedQueue<E>.removeAllByFilter(predicate: (E) -> Boolean)