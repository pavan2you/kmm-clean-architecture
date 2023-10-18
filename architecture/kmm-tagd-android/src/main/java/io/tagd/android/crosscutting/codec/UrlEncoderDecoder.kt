package io.tagd.android.crosscutting.codec

import io.tagd.arch.domain.crosscutting.codec.UrlCodec
import java.net.URLDecoder
import java.net.URLEncoder

class UrlEncoderDecoder : UrlCodec {

    override fun encode(data: String, charset: String) {
        URLEncoder.encode(data, charset)
    }

    override fun decode(data: String, charset: String) {
        URLDecoder.decode(data, charset)
    }

    override fun release() {
        //no-op
    }
}