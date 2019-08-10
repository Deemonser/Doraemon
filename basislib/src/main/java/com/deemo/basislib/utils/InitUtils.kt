package com.deemo.basislib.utils

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.deemo.basislib.BuildConfig


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    初始化的工具类
 */
object InitUtils {

    /**
     * 是否是 release 模式
     */
    var isRelease: Boolean = false
        private set(value) {
            field = value
        }


    /**
     * 是否是 Test 模式
     */
    var isTest: Boolean = false
        private set(value) {
            field = value
        }


    /**
     * 是否是 Debug 模式
     */
    var isDebug: Boolean = false
        private set(value) {
            field = value
        }


    fun init(application: Application) {
        syncBuildType()
        com.blankj.utilcode.util.Utils.init(application)
        AndroidThreeTen.init(application)

    }


    /**
     * Sync lib debug with app's debug value. Should be called in module Application
     *
     * @param context Context
     */
    private fun syncBuildType() {
        when (BuildConfig.BUILD_TYPE) {
            "release" -> isRelease = true
            "test" -> isTest = true
            "debug" -> isDebug = true
        }
    }
}
