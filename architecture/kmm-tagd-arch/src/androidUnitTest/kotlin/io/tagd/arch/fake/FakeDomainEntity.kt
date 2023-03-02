package io.tagd.arch.fake

import io.tagd.arch.domain.DomainEntity

class FakeDomainEntity(override val identifier: String) : DomainEntity<String>()