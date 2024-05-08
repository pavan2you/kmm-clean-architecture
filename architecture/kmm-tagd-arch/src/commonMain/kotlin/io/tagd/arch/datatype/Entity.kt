package io.tagd.arch.datatype

import io.tagd.arch.datatype.bind.BindableSubject

abstract class Entity<T> : Entitable<T>, BindableSubject() {

    override var crudOperation: CrudOperation = CrudOperation.CREATE

    override fun initialize() {
        crudOperation = CrudOperation.CREATE
    }

    override fun validate() {
        //no-op
    }
}