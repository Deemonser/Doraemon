package com.deemons.baselib.net.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    Token 失效自动跳转登录
 */
class TokenInterceptor @Inject constructor() : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        checkToken(response)

        return response
    }

    private fun checkToken(response: Response) {
        if (response.code() == 405) {

        }
    }

}
