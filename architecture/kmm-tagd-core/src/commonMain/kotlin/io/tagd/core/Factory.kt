package io.tagd.core

interface Factory : Service, Nameable {

    abstract class Builder<T : Factory> {

        protected var name: String? = null

        open fun name(name: String): Builder<T> {
            this.name = name
            return this
        }

        abstract fun build(): T
    }
}