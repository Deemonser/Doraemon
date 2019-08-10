package com.deemo.basislib.net.interceptor

import com.blankj.utilcode.util.EncryptUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

/**
 * author： deemo
 * date:    2019-08-05
 * desc:    加密
 */
class EncryptInterceptor @Inject constructor() : Interceptor {
    private val UTF8 = Charset.forName("UTF-8")

    private val First = "CreditMe"
    private val Last = "CreditMe"
    private val stringBuilder = StringBuilder()

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (originalRequest.method() == "POST" && originalRequest.body() != null) {
            val requestJson = getRequestJson(originalRequest)

            val builder = originalRequest.newBuilder()
            builder.addHeader("sign", sign(requestJson))

            return chain.proceed(builder.build())
        }
        return chain.proceed(originalRequest)
    }

    /**
     *  获取请求体Json
     */
    private fun getRequestJson(originalRequest: Request): String {
        val requestBody = originalRequest.body()!!

        val buffer = Buffer()
        requestBody.writeTo(buffer)

        var charset: Charset? = UTF8
        val contentType = requestBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(UTF8)
        }
        return buffer.readString(charset)
    }

    /**
     *  加签
     */
    private fun sign(json: String): String {
        stringBuilder.append(First)
        stringBuilder.append(json)
        stringBuilder.append(Last)
        val result = stringBuilder.toString()
        stringBuilder.clear()
        return EncryptUtils.encryptMD5ToString(result).toLowerCase()
    }

}

