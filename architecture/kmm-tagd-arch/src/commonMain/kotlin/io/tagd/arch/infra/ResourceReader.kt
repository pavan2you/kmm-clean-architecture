package io.tagd.arch.infra

import io.tagd.langx.IllegalValueException

interface ResourceReader : InfraService {

    fun resource(id: Int = 0, nameWithOrWithoutRelativePath: String? = null): UnifiedResource? {
        throw UnsupportedOperationException()
    }

    fun resource(
        id: Int = 0,
        nameWithOrWithoutRelativePath: String? = null,
        pkg: String? = null
    ): UnifiedResource {
        throw UnsupportedOperationException()
    }

    fun read(resource: Resource): String? {
        throw UnsupportedOperationException()
    }

    override fun release() {
        // no-op
    }
}

interface NamedResourceReader : ResourceReader {

    override fun read(resource: Resource): String? {
        return readNamed(resource as INamedResource)
    }

    fun readNamed(resource: INamedResource): String?
}

interface CompressedResourceReader : ResourceReader {

    fun readCompressed(resource: ICompressedResource): String?

    override fun read(resource: Resource): String? {
        return readCompressed(resource as ICompressedResource)
    }
}

interface UnifiedResourceReader : NamedResourceReader, CompressedResourceReader {

    override fun read(resource: Resource): String? {
        return when (resource) {
            is INamedResource -> {
                readNamed(resource)
            }

            is ICompressedResource -> {
                readCompressed(resource)
            }

            else -> {
                throw IllegalValueException("illegal $resource")
            }
        }
    }

    override fun readNamed(resource: INamedResource): String?

    override fun readCompressed(resource: ICompressedResource): String?

}