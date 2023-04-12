package io.tagd.android.crosscutting.codec

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.tagd.arch.domain.crosscutting.codec.JsonCodec
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.javaType

class GsonJsonCodec : JsonCodec {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var mutableGson: Gson? =
        GsonBuilder().registerTypeAdapterFactory(GsonTypeInitializerFactory()).create()

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

    override fun release() {
        mutableGson = null
    }
}