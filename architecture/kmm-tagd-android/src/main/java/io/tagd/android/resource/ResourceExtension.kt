package io.tagd.android.resource

import io.tagd.arch.infra.Resource

private const val drawable = "drawable"
private const val id = "id"
private const val string = "string"
private const val int = "int"
private const val float = "float"
private const val dimen = "dimen"

val Resource.Drawable: String
    get() = drawable

val Resource.Id: String
    get() = id

val Resource.String: String
    get() = string

val Resource.Int: String
    get() = int

val Resource.Float: String
    get() = float

val Resource.Dimen: String
    get() = dimen