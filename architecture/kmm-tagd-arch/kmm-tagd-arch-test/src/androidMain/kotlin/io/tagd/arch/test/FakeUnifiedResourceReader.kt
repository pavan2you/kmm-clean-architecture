package io.tagd.arch.test

import android.annotation.SuppressLint
import android.content.Context
import io.tagd.arch.access.crosscutting
import io.tagd.arch.domain.crosscutting.codec.JsonCodec
import io.tagd.arch.infra.CompressedResource
import io.tagd.arch.infra.ICompressedResource
import io.tagd.arch.infra.INamedResource
import io.tagd.arch.infra.NamedResource
import io.tagd.arch.infra.UnifiedResource
import io.tagd.arch.infra.UnifiedResourceReader
import io.tagd.di.typeOf
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

actual class FakeUnifiedResourceReader actual constructor(context: Any?) : UnifiedResourceReader {

    private var weakContext: WeakReference<Context>? = null

    init {
        assert(context is Context)
        weakContext = WeakReference(context as Context)
    }

    override fun resource(
        id: Int,
        nameWithOrWithoutRelativePath: String?,
        pkg: String?
    ): UnifiedResource {

        return UnifiedResource(nameWithOrWithoutRelativePath, "raw", id, pkg)
    }

    @SuppressLint("DiscouragedApi")
    override fun readNamed(resource: INamedResource): String? {
        val pkg = weakContext!!.get()!!.packageName
        val id = weakContext!!.get()!!.resources.getIdentifier(
            resource.nameWithOrWithoutRelativePath,
            "raw",
            pkg
        )
        return readCompressed(CompressedResource(type = "raw", identifier = id))
    }

    override fun readCompressed(resource: ICompressedResource): String? {
        return try {
            val fileId: Int = resource.identifier
            val `is`: InputStream = weakContext!!.get()!!.resources.openRawResource(fileId)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}

inline fun <reified T : Any> FakeUnifiedResourceReader.readNamedJson(fileName: String): T? {
    return readNamed(NamedResource(nameWithOrWithoutRelativePath = fileName))?.let { json ->
        crosscutting<JsonCodec>()?.fromJson(json, typeOf())
    }
}

inline fun <reified T : Any> FakeUnifiedResourceReader.readCompressedJson(id: Int): T? {
    return readCompressed(CompressedResource(identifier = id))?.let { json ->
        crosscutting<JsonCodec>()?.fromJson(json, typeOf())
    }
}