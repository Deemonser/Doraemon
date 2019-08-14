package com.deemons.doraemon.mvp.main

import com.blankj.utilcode.util.LogUtils
import com.deemons.doraemon.R
import com.deemons.doraemon.di.component.ActivityComponent
import com.deemons.doraemon.di.other.InjectActivity

class MainActivity : InjectActivity<MainPresenter>(), MainPresenter.View {
    override val layoutId: Int = R.layout.activity_main


    override fun componentInject(component: ActivityComponent) {
        component.inject(this)
    }

    override fun initEventAndData() {
        mPresenter.getData()
    }

    override fun test(testMsg: String) {
        LogUtils.d("test $testMsg")
    }
}
