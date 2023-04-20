package io.tagd.the101.android

import io.tagd.arch.domain.usecase.Args
import io.tagd.arch.domain.usecase.LiveUseCase
import io.tagd.arch.library.Library

class SampleLibrary(override val name: String) : Library {

    class Builder : Library.Builder<SampleLibrary>() {

        private var name: String? = null

        fun with(name: String) : Builder {
            this.name = name
            return this
        }

        override fun build(): SampleLibrary {
            return SampleLibrary(name ?: "sample")
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