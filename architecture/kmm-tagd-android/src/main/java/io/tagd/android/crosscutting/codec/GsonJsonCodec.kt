package io.tagd.android.crosscutting.codec

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.tagd.arch.domain.crosscutting.codec.JsonCodec
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.javaType

class GsonJsonCodec private constructor() : JsonCodec {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var mutableGsonBuilder: GsonBuilder =
        GsonBuilder().registerTypeAdapterFactory(GsonTypeInitializerFactory())

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var mutableGson: Gson? = mutableGsonBuilder.create()

    val gson
        get() = mutableGson!!

    override fun <T> toJson(obj: T): String {
        return gson.toJson(obj)
    }

    override fun <T : Any> fromJson(json: String, klass: KClass<T>): T {
        return gson.fromJson(json, klass.java)
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun <T> fromJson(json: String, type: KType): T {
        return gson.fromJson(json, type.javaType)
    }

    fun builder(): GsonBuilder {
        return mutableGsonBuilder
    }

    fun apply(builder: GsonBuilder = mutableGsonBuilder) {
        mutableGson = builder.create()
    }

    override fun release() {
        mutableGson = null
    }

    companion object {

        fun new(builder: (GsonBuilder.() -> GsonBuilder)? = null): GsonJsonCodec {
            return GsonJsonCodec().also {
                it.mutableGsonBuilder = GsonBuilder()
                    .registerTypeAdapterFactory(GsonTypeInitializerFactory())
                    .customize(builder)

                it.apply(it.mutableGsonBuilder)
            }
        }
    }
}

private fun GsonBuilder.customize(customizer: (GsonBuilder.() -> GsonBuilder)?): GsonBuilder {
    return customizer?.let { _customizer ->
        this._customizer()
    } ?: kotlin.run {
        this
    }
}