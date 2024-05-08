package io.tagd.arch.datatype

import io.tagd.arch.datatype.bind.BindableSubjectable
import io.tagd.core.Copyable
import io.tagd.core.Identifiable
import io.tagd.core.Mutable
import io.tagd.core.Validatable
import io.tagd.langx.datatype.Serializable

interface Entitable<T> : Identifiable<T>, Mutable, Copyable, Validatable, Serializable,
    CrudOperational, BindableSubjectable