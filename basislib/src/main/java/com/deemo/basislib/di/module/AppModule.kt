package com.deemo.basislib.di.module


import com.deemo.basislib.base.BaseApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * author： Deemo
 * date:    2019-07-05
 * desc:    提供全局依赖
 */
@Module
class AppModule constructor(private val app: BaseApp) {

    @Singleton
    @Provides
    fun provideBaseApp(): BaseApp = app


}


