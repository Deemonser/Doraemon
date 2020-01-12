package com.deemons.baselib.utils

import android.app.Application
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.deemons.baselib.BuildConfig
import com.deemons.baselib.constants.SPKey
import com.jakewharton.threetenabp.AndroidThreeTen
import java.util.*


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    初始化的工具类
 */
object InitUtils {

    /**
     * 是否是 release 模式
     */
    const val isRelease: Boolean = BuildConfig.BUILD_TYPE == "release"


    /**
     * 是否是 check模式
     */
    const val isCheck: Boolean = BuildConfig.BUILD_TYPE == "check"


    /**
     * 是否是 Debug 模式
     */
    const val isDebug: Boolean = BuildConfig.BUILD_TYPE == "debug"


    /**
     *  工具初始化
     */
    fun init(application: Application) {
        com.blankj.utilcode.util.Utils.init(application)
        AndroidThreeTen.init(application)

        if (SPUtils.getInstance().getString(SPKey.UUID, "").isBlank()) {
            SPUtils.getInstance().put(SPKey.UUID, UUID.randomUUID().toString(), true)
        }

        LogUtils.getConfig().isLogSwitch = !isRelease

    }

    /**
     * 获取 UUID
     */
    fun getUUID() = SPUtils.getInstance().getString(SPKey.UUID, "")

}
