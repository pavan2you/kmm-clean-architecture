package io.tagd.android.crosscutting.codec

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.tagd.arch.crosscutting.codec.JsonCodec
import io.tagd.langx.reflection.Type
import kotlin.reflect.KClass

class GsonJsonCodec private constructor() : JsonCodec<GsonBuilder> {

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

    override fun <T> fromJson(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }

    override fun <T : Any> fromMap(map: Map<String, Any?>?, klass: KClass<T>): T {
        val json = toJson(map)
        return fromJson(json, klass)
    }

    override fun builder(): GsonBuilder {
        return mutableGsonBuilder
    }

    override fun apply(builder: GsonBuilder) {
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