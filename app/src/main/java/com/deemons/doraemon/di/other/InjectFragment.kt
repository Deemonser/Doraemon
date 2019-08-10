package com.deemons.doraemon.di.other

import com.deemo.basislib.base.BaseFragment
import com.deemo.basislib.mvp.IPresenter
import com.deemons.doraemon.di.component.FragmentComponent
import com.deemons.doraemon.di.helper.ComponentHelper
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

    abstract fun componentInject(component: FragmentComponent)
}