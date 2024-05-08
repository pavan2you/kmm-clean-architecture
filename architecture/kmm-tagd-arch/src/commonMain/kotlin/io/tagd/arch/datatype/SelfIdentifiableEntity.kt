package io.tagd.arch.datatype

open class SelfIdentifiableEntity : Entity<String>() {

    override val identifier: String
        get() = this::class.simpleName.toString()
}