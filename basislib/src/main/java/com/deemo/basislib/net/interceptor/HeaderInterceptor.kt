package com.deemo.basislib.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    请求头拦截器
 */
class HeaderInterceptor(
    private val headers: MutableMap<String, String>,
    private val mCallback: (String, MutableMap<String, String>) -> Unit
) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()

        mCallback(chain.request().url().toString(), headers)

        for ((key, value) in headers) {
            builder.addHeader(key, value)
        }

        return chain.proceed(builder.build())
    }


}