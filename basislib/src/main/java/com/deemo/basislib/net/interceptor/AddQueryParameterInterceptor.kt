package com.deemo.basislib.net.interceptor

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URLDecoder
import javax.inject.Inject

/**
 * author： deemo
 * date:    2019-08-05
 * desc:    表单提交转 json提交
 */
class AddQueryParameterInterceptor @Inject constructor(val gson: Gson) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        if (originalRequest.method() == "POST") {
            val requestBody = originalRequest.body()
            if (requestBody is FormBody || (requestBody?.contentType() == null && requestBody?.contentLength() == 0L)) {
                val treeMap = LinkedHashMap<String?, Any?>()
                if (requestBody is FormBody) {
                    for (i in 0 until requestBody.size()) {
                        treeMap[requestBody.encodedName(i)] = requestBody.encodedValue(i)
                    }
                }
                builder.post(
                    RequestBody.create(
                        MediaType.parse("application/json"),
                        URLDecoder.decode(gson.toJson(treeMap), "utf-8")
                    )
                )
            }
        }
        return chain.proceed(builder.build())
    }

}

