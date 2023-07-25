package io.tagd.arch.infra

import io.tagd.arch.access.crosscutting
import io.tagd.arch.domain.crosscutting.codec.JsonCodec
import io.tagd.di.typeOf
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

inline fun <reified T : Any> UnifiedResourceReader.readNamedJson(fileName: String): T? {
    return readNamed(NamedResource(nameWithOrWithoutRelativePath = fileName))?.let { json ->
        crosscutting<JsonCodec<*>>()?.fromJson(json, typeOf())
    }
}

inline fun <reified T : Any> UnifiedResourceReader.readCompressedJson(id: Int): T? {
    return readCompressed(CompressedResource(identifier = id))?.let { json ->
        crosscutting<JsonCodec<*>>()?.fromJson(json, typeOf())
    }
}