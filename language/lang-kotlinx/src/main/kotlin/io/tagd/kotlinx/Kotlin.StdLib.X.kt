package io.tagd.kotlinx

inline fun <A, B, R> let(one: A?, two: B?, block: (A, B) -> R): R? {
    return if (one != null && two != null) {
        block.invoke(one, two)
    } else {
        null
    }
}

inline fun <A, B, C, R> let(one: A?, two: B?, three: C?, block: (A, B, C) -> R): R? {
    return if (one != null && two != null && three != null) {
        block.invoke(one, two, three)
    } else {
        null
    }
}

inline fun <A, R> let(
    one: A?,
    noinline `finally`: UnitBlock,
    block: (A, UnitBlock) -> R
): R? {

    return if (one != null) {
        block.invoke(one, `finally`)
    } else {
        `finally`.invoke(Unit)
        null
    }
}

inline fun <A, B, R> let(
    one: A?,
    two: B?,
    noinline `finally`: UnitBlock,
    block: (A, B, UnitBlock) -> R
): R? {

    return if (one != null && two != null) {
        block.invoke(one, two, `finally`)
    } else {
        `finally`.invoke(Unit)
        null
    }
}

inline fun <A, B, C, R> let(
    one: A?,
    two: B?,
    three: C?,
    noinline `finally`: UnitBlock,
    block: (A, B, C, UnitBlock) -> R
): R? {

    return if (one != null && two != null && three != null) {
        block.invoke(one, two, three, `finally`)
    } else {
        `finally`.invoke(Unit)
        null
    }
}

inline fun <A, B, C, D, R> let(
    one: A?,
    two: B?,
    three: C?,
    four: D?,
    noinline `finally`: UnitBlock,
    block: (A, B, C, D, UnitBlock) -> R
): R? {

    return if (one != null && two != null && three != null && four != null) {
        block.invoke(one, two, three, four, `finally`)
    } else {
        `finally`.invoke(Unit)
        null
    }
}

@JvmName("letT")
inline fun <T, R> T.let(noinline `finally`: UnitBlock, block: (T, UnitBlock) -> R): R {
    return block(this, finally)
}

infix fun <A, B> A.and(that: B): Pair<A, B> = Pair(this, that)

infix fun <A, B, C> Pair<A, B>.and(that: C): Triple<A, B, C> =
    Triple(first = this.first, second = this.second, third = that)

infix fun <A, B> A.comma(that: B): Pair<A, B> = Pair(this, that)

infix fun <A, B, C> Pair<A, B>.comma(that: C): Triple<A, B, C> =
    Triple(first = this.first, second = this.second, third = that)

inline fun <A, B, R> Pair<A?, B?>.let(block: (A, B) -> R): R? {
    return if (first != null && second != null) {
        block.invoke(first!!, second!!)
    } else {
        null
    }
}

inline fun <A, B, C, R> Triple<A?, B?, C?>.let(block: (A, B, C) -> R): R? {
    return if (first != null && second != null && third != null) {
        block.invoke(first!!, second!!, third!!)
    } else {
        null
    }
}

inline fun <A, B, R> Pair<A?, B?>.let(
    noinline `finally`: UnitBlock,
    block: (A, B, UnitBlock) -> R
): R? {

    return if (first != null && second != null) {
        block.invoke(first!!, second!!, finally)
    } else {
        `finally`.invoke(Unit)
        null
    }
}

inline fun <A, B, C, R> Triple<A?, B?, C?>.let(
    noinline `finally`: UnitBlock,
    block: (A, B, C, UnitBlock) -> R
): R? {

    return if (first != null && second != null && third != null) {
        block.invoke(first!!, second!!, third!!, finally)
    } else {
        `finally`.invoke(Unit)
        null
    }
}

typealias GenericBlock<T> = (T) -> Unit

typealias UnitBlock = (Unit) -> Unit