package com.deemons.doraemon.di.other

import com.deemo.basislib.base.BaseActivity
import com.deemo.basislib.mvp.IPresenter
import com.deemons.doraemon.di.component.ActivityComponent
import com.deemons.doraemon.di.helper.ComponentHelper
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


    abstract override val layoutId: Int

    abstract fun componentInject(component: ActivityComponent)


}