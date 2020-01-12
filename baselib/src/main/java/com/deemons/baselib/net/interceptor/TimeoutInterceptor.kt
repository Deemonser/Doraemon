package com.deemons.baselib.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * author： deemo
 * date:    2019-09-04
 * desc:    动态设置接口请求超时时间
 */
class TimeoutInterceptor @Inject constructor() : Interceptor {


    val CONNECT_TIMEOUT = "CONNECT_TIMEOUT"
    val READ_TIMEOUT = "READ_TIMEOUT"
    val WRITE_TIMEOUT = "WRITE_TIMEOUT"



    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        var connectTimeout = chain.connectTimeoutMillis()
        var readTimeout = chain.readTimeoutMillis()
        var writeTimeout = chain.writeTimeoutMillis()

        request.header(CONNECT_TIMEOUT)?.let {
            if (it.isNotBlank()) connectTimeout = it.toInt()
        }
        request.header(READ_TIMEOUT)?.let {
            if (it.isNotBlank()) readTimeout = it.toInt()
        }
        request.header(WRITE_TIMEOUT)?.let {
            if (it.isNotBlank()) writeTimeout = it.toInt()
        }


        return chain
            .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
            .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
            .proceed(request)
    }
}