package io.tagd.the101.android

import io.tagd.arch.domain.usecase.Args
import io.tagd.arch.domain.usecase.LiveUseCase
import io.tagd.arch.library.AbstractLibrary
import io.tagd.arch.library.Library
import io.tagd.di.Global
import io.tagd.di.Scope

class SampleLibrary private constructor(name: String, scope: Scope) : AbstractLibrary(name, scope) {

    class Builder : Library.Builder<SampleLibrary>() {

        override fun buildLibrary(): SampleLibrary {
            return SampleLibrary(name ?: "sample", Global)
        }
    }

    override fun release() {
        //no-op
    }
}

class LibraryUsecase : LiveUseCase<Unit>() {
    override fun trigger(args: Args) {
        println("inside library's usecase")
    }

}