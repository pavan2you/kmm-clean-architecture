@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package io.tagd.langx.collection

expect class ArrayDeque<E>() : Deque<E> {

    constructor(elements: Collection<E>)
}