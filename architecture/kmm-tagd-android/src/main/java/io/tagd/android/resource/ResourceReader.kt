package io.tagd.android.resource

import android.annotation.SuppressLint
import android.content.Context
import io.tagd.arch.infra.CompressedResource
import io.tagd.arch.infra.ICompressedResource
import io.tagd.arch.infra.INamedResource
import io.tagd.arch.infra.NamedResourceReader
import io.tagd.arch.infra.UnifiedResource
import io.tagd.arch.infra.UnifiedResourceReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

open class JavaResourceReader : NamedResourceReader {

    override fun readNamed(resource: INamedResource): String? {
        return read(fileName = resource.nameWithOrWithoutRelativePath!!)
    }

    private fun read(fileName: String): String {
        val file = open(fileName)
        return read(file)
    }

    private fun open(fileName: String): File {
        return File(resourcePath(fileName) ?: "")
    }

    private fun resourcePath(fileName: String) = javaClass.getResource("/${fileName}")?.path

    private fun read(file: File): String {
        val fileReader = FileReader(file)
        val result = fileReader.readText()
        fileReader.close()
        return result
    }
}

open class ContextResourceReader(context: Any?) : UnifiedResourceReader {

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

        return UnifiedResource(nameWithOrWithoutRelativePath, null, id, pkg)
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