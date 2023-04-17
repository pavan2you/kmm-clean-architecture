package io.tagd.arch.test

import io.tagd.arch.infra.ICompressedResource
import io.tagd.arch.infra.INamedResource
import io.tagd.arch.infra.UnifiedResourceReader

actual class FakeUnifiedResourceReader actual constructor(context: Any?) :
    UnifiedResourceReader {

    override fun readNamed(resource: INamedResource): String? {
        TODO("Not yet implemented")
    }

    override fun readCompressed(resource: ICompressedResource): String? {
        TODO("Not yet implemented")
    }
}