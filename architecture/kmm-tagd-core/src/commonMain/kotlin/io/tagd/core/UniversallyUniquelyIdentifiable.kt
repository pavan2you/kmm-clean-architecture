package io.tagd.core

import io.tagd.langx.UUID

open class UniversallyUniquelyIdentifiable(override val identifier: UUID = UUID()) :
    Identifiable<UUID>