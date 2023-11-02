package io.tagd.langx.collection

import io.tagd.langx.ref.WeakReference

/**
 * E must E?
 * The client code must ensure that E must E?, to avoid NPEs, because actual E is held as a
 * WeakReference.
 *
 * Most of the functions are O(N), since we have to loop through all the elements to find out
 * the actual E is there or not.
 */
@Suppress("UNCHECKED_CAST")
class WeakArrayList<E> : MutableList<E>, RandomAccess {

    private var list = ArrayList<WeakReference<E>>()

    override val size: Int
        get() = list.size

    override fun clear() {
        list.clear()
    }

    override fun addAll(elements: Collection<E>): Boolean {
        var result = false
        elements.forEach { element ->
            result = result and add(element)
        }
        return result
    }

    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        val weakElements = arrayListOf<WeakReference<E>>()
        elements.forEach {
            weakElements.add(WeakReference(it))
        }
        return list.addAll(index, weakElements)
    }

    override fun add(index: Int, element: E) {
        list.add(index, WeakReference(element))
    }

    override fun add(element: E): Boolean {
        return addInternal(WeakReference(element))
    }

    override fun get(index: Int): E {
        return list[index].get() as E // in reality it is --> as E?
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override fun iterator(): MutableIterator<E> {
        return MutableIteratorInternal(list)
    }

    override fun listIterator(): MutableListIterator<E> {
        return MutableListIteratorInternal(list)
    }

    override fun listIterator(index: Int): MutableListIterator<E> {
        return MutableListIteratorInternal(list, index)
    }

    override fun removeAt(index: Int): E {
        return list.removeAt(index).get() as E // in reality it is --> as E?
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
        return WeakArrayList<E>().also { subList ->
            for (i in fromIndex..toIndex) {
                subList.addInternal(list[i])
            }
        }
    }

    private fun addInternal(weakElement: WeakReference<E>): Boolean {
        return list.add(weakElement)
    }

    override fun set(index: Int, element: E): E {
        return list.set(index, WeakReference(element)).get() as E // in reality it is --> as E?
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        return if (containsAll(elements)) {
            list.clear()
            addAll(elements)
        } else {
            false
        }
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        var result = false
        elements.forEach { element ->
            result = result and remove(element)
        }
        return result
    }

    override fun remove(element: E): Boolean {
        var result = false
        for (i in  0 until list.size) {
            if (list[i].get() == element) {
                list.removeAt(i)
                result = true
                break
            }
        }
        return result
    }

    override fun lastIndexOf(element: E): Int {
        var counter = -1
        var lastIndex = -1
        list.forEach {
            counter++
            if (it.get() == element) {
                lastIndex = counter
            }
        }
        return lastIndex
    }

    override fun indexOf(element: E): Int {
        var counter = -1
        var index = -1
        list.forEach {
            counter++
            if (it.get() == element) {
                index = counter
                return@forEach
            }
        }
        return index
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        var result = false
        elements.forEach { element ->
            result = result and contains(element)
        }
        return result
    }

    override fun contains(element: E): Boolean {
        var result = false
        list.forEach {
            if (it.get() == element) {
                result = true
                return@forEach
            }
        }
        return result
    }

    private class MutableIteratorInternal<E>(list: ArrayList<WeakReference<E>>) :
        MutableIterator<E> {

        private var delegate = list.iterator()

        override fun hasNext(): Boolean {
            return delegate.hasNext()
        }

        override fun next(): E {
            return delegate.next().get() as E // in reality it is --> as E?
        }

        override fun remove() {
            delegate.remove()
        }
    }

    private class MutableListIteratorInternal<E>(
        list: ArrayList<WeakReference<E>>,
        index: Int = 0
    ) : MutableListIterator<E> {

        private var delegate = list.listIterator(index)

        override fun add(element: E) {
            delegate.add(WeakReference(element))
        }

        override fun hasNext(): Boolean {
            return delegate.hasNext()
        }

        override fun hasPrevious(): Boolean {
            return delegate.hasPrevious()
        }

        override fun next(): E {
            return delegate.next() as E // in reality it is --> as E?
        }

        override fun nextIndex(): Int {
            return delegate.nextIndex()
        }

        override fun previous(): E {
            return delegate.previous().get() as E // in reality it is --> as E?
        }

        override fun previousIndex(): Int {
            return delegate.previousIndex()
        }

        override fun remove() {
            return delegate.remove()
        }

        override fun set(element: E) {
            delegate.set(WeakReference(element))
        }

    }
}

inline fun <E> weakArrayListOf() : WeakArrayList<E> {
    return WeakArrayList()
}