package io.tagd.core

interface LatelyInjectable {

    fun latelyInjectWith(vararg args: Any?)
}