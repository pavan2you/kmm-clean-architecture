package io.tagd.arch.datatype

import io.tagd.arch.domain.DomainEntity

open class SelfIdentifiableDomainEntity : DomainEntity<String>() {

    override val identifier: String
        get() = this::class.simpleName.toString()
}