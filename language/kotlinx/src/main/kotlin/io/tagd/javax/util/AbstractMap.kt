package io.tagd.javax.util

import java.util.AbstractMap

fun <K, V> AbstractMap<K, V>.getKeys(): List<K> {
    val keys = arrayListOf<K>()
    this.entries.forEach {
        keys.add(it.key)
    }
    return keys
}