package com.deemons.baselib.net.interceptor

import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.URLDecoder
import javax.inject.Inject

/**
 * author： deemo
 * date:    2019-08-05
 * desc:    表单提交转 json提交
 */
class ParameterInterceptor @Inject constructor(val gson: Gson) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        if (originalRequest.method() == "POST") {
            val requestBody = originalRequest.body()
            if (requestBody is FormBody || (requestBody?.contentType() == null && requestBody?.contentLength() == 0L)) {
                val json = JSONObject()
                if (requestBody is FormBody) {
                    for (i in 0 until requestBody.size()) {
                        val encodedValue = URLDecoder.decode(requestBody.encodedValue(i), "utf-8")
                        if (encodedValue.startsWith('{') && encodedValue.endsWith('}')) {
                            json.put(requestBody.encodedName(i), JSONObject(encodedValue))
                        } else {
                            json.put(requestBody.encodedName(i), encodedValue)
                        }
                    }
                }

                builder.post(
                    RequestBody.create(
                        MediaType.parse("application/json"),
                        json.toString()
                    )
                )
            }
        }
        return chain.proceed(builder.build())
    }

}

