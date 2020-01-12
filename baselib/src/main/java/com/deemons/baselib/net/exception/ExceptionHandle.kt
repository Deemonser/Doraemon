package com.deemons.baselib.net.exception

import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.LogUtils
import com.deemons.baselib.arouter.ARouterKey
import com.deemons.baselib.di.module.NetworkModule
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
        val errorMsg = ""
        LogUtils.e(e)
        return when (e) {
            is ConnectException -> errorMsg
            is HttpException -> handleHttp(e)
            is ParseException, is JSONException -> errorMsg
            else -> errorMsg
        }

    }


    private fun handleHttp(httpException: HttpException): String? {
        return when (httpException.code()) {
            UNAUTHORIZED, FORBIDDEN -> {
                NetworkModule.token = ""
                ARouter.getInstance().build(ARouterKey.LoginActivity).navigation()
                return null
            }
            else -> ""
        }
    }
}
