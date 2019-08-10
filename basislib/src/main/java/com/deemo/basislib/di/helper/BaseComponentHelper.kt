package com.deemo.basislib.di.helper

import com.blankj.utilcode.util.Utils
import com.deemo.basislib.base.BaseApp
import com.deemo.basislib.di.component.DaggerAppComponent
import com.deemo.basislib.di.module.AppModule
import com.deemo.basislib.di.module.NetworkModule

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