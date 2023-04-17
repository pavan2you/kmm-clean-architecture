package io.tagd.arch.test

import io.tagd.arch.domain.DomainEntity

class FakeDomainEntity(override val identifier: String) : DomainEntity<String>()