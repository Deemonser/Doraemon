package com.deemons.server.di.other

import com.deemons.baselib.base.BaseFragment
import com.deemons.baselib.mvp.IPresenter
import com.deemons.server.di.component.FragmentComponent
import com.deemons.server.di.helper.ComponentHelper
import javax.inject.Inject

/**
 * author： Deemo
 * date:    2019-07-05
 * desc:
 */
abstract class InjectFragment<P : IPresenter> : BaseFragment() {

    @Inject
    lateinit var mPresenter: P


    override fun initComponent() {
        componentInject(ComponentHelper.fragmentComponent)

        mPresenter.attachView(this)
        lifecycle.addObserver(mPresenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }


    abstract fun componentInject(component: FragmentComponent)
}