package com.deemons.server.di.other

import com.deemons.baselib.base.BaseActivity
import com.deemons.baselib.mvp.IPresenter
import com.deemons.server.di.component.ActivityComponent
import com.deemons.server.di.helper.ComponentHelper
import javax.inject.Inject

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
abstract class InjectActivity<P : IPresenter> : BaseActivity() {

    @Inject
    lateinit var mPresenter: P


    override fun initComponent() {
        componentInject(ComponentHelper.activityComponent)

        mPresenter.attachView(this)
        lifecycle.addObserver(mPresenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }


    abstract override val layoutId: Int

    abstract fun componentInject(component: ActivityComponent)


}