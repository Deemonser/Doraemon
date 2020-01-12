package com.deemons.server.mvp.launch

import com.alibaba.android.arouter.facade.annotation.Route
import com.deemons.baselib.arouter.ARouterKey
import com.deemons.server.R
import com.deemons.server.di.component.ActivityComponent
import com.deemons.server.di.other.InjectActivity

@Route(path = ARouterKey.SplashActivity)
class SplashActivity : InjectActivity<SplashPresenter>() {


    override val layoutId: Int= R.layout.activity_splash

    override fun componentInject(component: ActivityComponent) {
        component.inject(this)
    }

    override fun initEventAndData() {

        mPresenter.getInfo()

    }
}
