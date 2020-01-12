package com.deemons.server.mvp.launch

import com.deemons.baselib.base.BasePresenter
import com.deemons.baselib.mvp.IView
import com.deemons.server.service.NetService
import javax.inject.Inject

/**
 * author： deemo
 * date:    2019-07-27
 * desc:    启动页逻辑处理
 */
class SplashPresenter @Inject constructor(private val netService: NetService) :
    BasePresenter<SplashPresenter.View>() {


    fun getInfo() {

    }


    interface View : IView {


    }
}