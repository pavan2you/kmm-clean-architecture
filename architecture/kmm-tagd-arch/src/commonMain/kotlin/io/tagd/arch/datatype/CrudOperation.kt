package io.tagd.arch.datatype

enum class CrudOperation(val value: String) {
    CREATE("C"),
    UPDATE("U"),
    DELETE("D"),
    READ("R")
}