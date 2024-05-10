package io.tagd.arch.datatype

import io.tagd.arch.datatype.bind.BindableSubjectable
import io.tagd.core.Identifiable
import io.tagd.core.Mutable

interface Entitable<T> : Identifiable<T>, Mutable, DomainObject, CrudOperational,
    BindableSubjectable