package com.deemons.baselib.di.helper

import com.blankj.utilcode.util.Utils
import com.deemons.baselib.base.BaseApp
import com.deemons.baselib.di.component.DaggerAppComponent
import com.deemons.baselib.di.module.AppModule
import com.deemons.baselib.di.module.NetworkModule

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    提供全局的 component
 */
object BaseComponentHelper {


    val appComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(Utils.getApp() as BaseApp))
            .networkModule(NetworkModule())
            .build()
    }


}