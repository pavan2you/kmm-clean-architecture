package io.tagd.arch.datatype

data class Property<T>(val name: String, val value: T) : DataObject()
