package io.tagd.arch.datatype

import io.tagd.arch.datatype.bind.BindableSubjectable

interface DataObjectable : ValueObject, CrudOperational, BindableSubjectable