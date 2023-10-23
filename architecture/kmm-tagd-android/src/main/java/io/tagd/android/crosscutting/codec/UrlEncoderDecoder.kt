package io.tagd.android.crosscutting.codec

import io.tagd.arch.domain.crosscutting.codec.UrlCodec
import java.net.URLDecoder
import java.net.URLEncoder

class UrlEncoderDecoder : UrlCodec {

    override fun encode(data: String, charset: String): String {
        return URLEncoder.encode(data, charset)
    }

    override fun decode(data: String, charset: String): String {
        return URLDecoder.decode(data, charset)
    }

    override fun release() {
        //no-op
    }
}