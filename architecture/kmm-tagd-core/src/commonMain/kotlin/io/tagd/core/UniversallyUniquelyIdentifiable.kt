package io.tagd.core

import io.tagd.langx.datatype.UUID

open class UniversallyUniquelyIdentifiable(override val identifier: UUID = UUID()) :
    Identifiable<UUID>