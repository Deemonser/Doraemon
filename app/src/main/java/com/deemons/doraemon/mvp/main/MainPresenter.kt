package com.deemons.doraemon.mvp.main

import com.deemo.basislib.base.BasePresenter
import com.deemo.basislib.exp.rx.simpleBind
import com.deemo.basislib.exp.rx.sub
import com.deemo.basislib.mvp.IView
import com.deemons.doraemon.service.NetService
import javax.inject.Inject

/**
 * author： deemo
 * date:    2019-08-14
 * desc:
 */
class MainPresenter @Inject constructor(val netService: NetService) : BasePresenter<MainPresenter.View>() {


    fun getData() {
        netService.getVersion()
            .simpleBind(mView)
            .sub(this, {
                mView?.showMessage(it.message ?: return@sub)
            }) {
                mView?.test("sdfadf")
            }

    }


    interface View : IView {

        fun test(testMsg: String)
    }

}
