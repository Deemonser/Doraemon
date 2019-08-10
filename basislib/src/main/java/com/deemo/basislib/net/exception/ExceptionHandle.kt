package com.deemo.basislib.net.exception

import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.deemo.basislib.arouter.ARouterKey
import com.deemo.basislib.di.module.NetworkModule
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.text.ParseException

object ExceptionHandle {

    val UNAUTHORIZED = 401
    val FORBIDDEN = 403
    val NOT_FOUND = 404
    val REQUEST_TIMEOUT = 408
    val INTERNAL_SERVER_ERROR = 500
    val BAD_GATEWAY = 502
    val SERVICE_UNAVAILABLE = 503
    val GATEWAY_TIMEOUT = 504

    /**
     *  错误处理
     *  返回 String 不为空，则显示，否则只是打印日志
     */
    fun handleException(e: Throwable): String? {
        LogUtils.e(e)
        return when (e) {
            is ConnectException -> "Connect error"
            is HttpException -> handleHttp(e)
            is ParseException, is JSONException -> "Parse json error"
            else -> "Unknown error: ${e.message}"
        }

    }


    private fun handleHttp(httpException: HttpException): String? {
        return when (httpException.code()) {
            UNAUTHORIZED, FORBIDDEN -> {
                NetworkModule.token = ""
                ARouter.getInstance().build(ARouterKey.LoginActivity).navigation()
                return null
            }
            else -> "Http error ${httpException.code()} ${httpException.message()}"
        }
    }
}
