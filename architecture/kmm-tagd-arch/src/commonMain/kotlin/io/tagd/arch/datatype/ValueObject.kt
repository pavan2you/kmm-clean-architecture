package io.tagd.arch.datatype

import io.tagd.core.Copyable
import io.tagd.core.Immutable
import io.tagd.core.Validatable
import io.tagd.langx.datatype.Serializable

interface ValueObject : Immutable, Copyable, Validatable, Serializable