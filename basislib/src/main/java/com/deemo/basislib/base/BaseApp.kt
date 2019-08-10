package com.deemo.basislib.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.deemo.basislib.utils.InitUtils
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
open class BaseApp : Application() {


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        //工具初始化
        InitUtils.init(this)

        configAutoSize()

        initARouter()


    }


    private fun configAutoSize() {
        //为规避屏幕适配带来的影响，所以使用副单位
        AutoSizeConfig.getInstance().unitsManager
            .setSupportSP(false)
            .setSupportDP(false).supportSubunits = Subunits.MM
    }


    /**
     * 初始化 ARouter
     */
    private fun initARouter() {
        if (!InitUtils.isRelease) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()  // 开启调试模式
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }
}
