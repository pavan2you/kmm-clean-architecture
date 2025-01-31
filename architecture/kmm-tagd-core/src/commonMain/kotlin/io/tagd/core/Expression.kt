package io.tagd.core

interface Expression {

    fun <T> evaluate(): T
}