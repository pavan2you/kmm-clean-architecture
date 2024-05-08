package io.tagd.arch.test

import io.tagd.arch.domain.DomainEntity

class FakeDomainEntity : DomainEntity<String>() {
    override val identifier: String
        get() = "FakeDomainEntity"
}