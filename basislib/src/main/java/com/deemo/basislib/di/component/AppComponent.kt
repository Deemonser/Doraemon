package com.deemo.basislib.di.component

import com.deemo.basislib.arouter.JsonServiceImpl
import com.deemo.basislib.di.module.AppModule
import com.deemo.basislib.di.module.NetworkModule
import com.deemo.basislib.net.api.Api
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