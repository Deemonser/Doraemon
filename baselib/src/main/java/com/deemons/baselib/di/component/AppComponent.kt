package com.deemons.baselib.di.component

import com.deemons.baselib.arouter.JsonServiceImpl
import com.deemons.baselib.di.module.AppModule
import com.deemons.baselib.di.module.NetworkModule
import com.deemons.baselib.net.api.Api
import com.google.gson.Gson
import dagger.Component
import javax.inject.Singleton

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
@Singleton
@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun inject(jsonServiceImpl: JsonServiceImpl)

    fun provideApi(): Api

    fun provideGson(): Gson

}